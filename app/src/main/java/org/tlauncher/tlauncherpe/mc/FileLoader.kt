package org.tlauncher.tlauncherpe.mc

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import okio.*
import java.io.FileOutputStream
import java.io.IOException
import java.net.SocketException
import java.util.concurrent.TimeUnit

object FileLoader {
    private val loadsMap: MutableMap<Int, Pair<Subject<Progress>, Disposable>> = mutableMapOf()

    data class Progress(val bytesRead: Long, val contentLength: Long)


    fun load(id: Int, url: String, path: String): Observable<Progress> {
        val subject = BehaviorSubject.create<Progress>()
        val obs = Observable.create<Progress> { emitter ->
            val request = Request.Builder()
                    .url(url)
                    .build()

            val progressListener = object : ProgressListener {
                override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {
                    if (done) emitter.onComplete()
                    else emitter.onNext(Progress(bytesRead, contentLength))
                }
            }

            val client = OkHttpClient.Builder()
                    .addNetworkInterceptor({ chain ->
                        val originalResponse = chain.proceed(chain.request())
                        return@addNetworkInterceptor originalResponse.newBuilder()
                                .body(ProgressResponseBody(originalResponse.body(), progressListener))
                                .build()
                    })
                    .readTimeout(30,TimeUnit.SECONDS)
                    .writeTimeout(30,TimeUnit.SECONDS)
                    .build()

            val call = client.newCall(request)
            emitter.setCancellable {
                if (call.isExecuted) call.cancel()
            }
            call.execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code " + response)
                response.body().byteStream().use { input ->
                    val fos = FileOutputStream(path)
                    fos.use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }.subscribeOn(Schedulers.io())
        val disposable: Disposable = obs.subscribe(subject::onNext, subject::onError, subject::onComplete)
        loadsMap.put(id, Pair(subject, disposable))
        return subject
    }

    fun cancelLoad(id: Int) {
        loadsMap[id]?.second?.dispose()
        loadsMap.remove(id)
    }

    fun getLoad(id: Int): Observable<Progress> = loadsMap[id]?.first ?: Observable.empty()

}



private class ProgressResponseBody internal constructor(private val responseBody: ResponseBody, private val progressListener: ProgressListener) : ResponseBody() {
    private var bufferedSource: BufferedSource? = null

    override fun contentType(): MediaType {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun source(): BufferedSource? {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()))
        }
        return bufferedSource
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            internal var totalBytesRead = 0L

            override fun read(sink: Buffer, byteCount: Long): Long {
                try {
                    val bytesRead = super.read(sink, byteCount)
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += if (bytesRead != -1.toLong()) bytesRead else 0
                    progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1.toLong())
                    return bytesRead
                } catch (ex: SocketException) {
                    return -1
                }
            }
        }
    }
}

internal interface ProgressListener {
    fun update(bytesRead: Long, contentLength: Long, done: Boolean)
}