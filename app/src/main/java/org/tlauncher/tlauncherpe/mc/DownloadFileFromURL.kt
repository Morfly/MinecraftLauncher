package org.tlauncher.tlauncherpe.mc

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.BufferedInputStream
import java.io.File
import java.io.InputStream
import java.net.URL


fun downloadFileWithProgress(stringUrl: String, file: File): Observable<Int> =
        Observable.create<Int>({
            var isInterrupted: Boolean = false
            val url = URL(stringUrl)
            val connection = url.openConnection()
            connection.connect()
            val fileLength = connection.contentLength
            BufferedInputStream(url.openStream(), 8192).use { `in` ->
                file.outputStream().use { out ->
                    `in`.toIterableWithProgress().forEach { (byteArray, total) ->
                        run {
                            if(isInterrupted) return@use
                            out.write(byteArray)
                            it.onNext((total * 100) / fileLength)
                        }
                    }
                }
            }
            it.onComplete()
        }).subscribeOn(Schedulers.io())

fun InputStream.toIterableWithProgress() = object : Iterable<Pair<ByteArray, Int>> {
    override fun iterator() = object : Iterator<Pair<ByteArray, Int>> {
        private var byteArray = ByteArray(2048)
        private var bytes = 0
        private var sum = 0
        override fun hasNext(): Boolean {
            bytes = this@toIterableWithProgress.read(byteArray)
            return bytes != -1
        }

        override fun next(): Pair<ByteArray, Int> {
            sum += bytes
            return Pair(byteArray, sum)
        }
    }
}

