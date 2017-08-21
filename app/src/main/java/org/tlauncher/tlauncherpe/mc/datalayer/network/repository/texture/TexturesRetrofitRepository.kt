package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.texture

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.ResponseBody
import org.tlauncher.tlauncherpe.mc.datalayer.db.DbManager
import org.tlauncher.tlauncherpe.mc.datalayer.network.RestApi
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MyTextures
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Textures
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectDownloadResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectTypesResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.TexturesResponse
import javax.inject.Inject


class TexturesRetrofitRepository
@Inject
constructor(private val restApi: RestApi, private val database: DbManager): TexturesRepository{

    override fun getTexturesListFromDb(): Observable<Textures> {
        return database.getTextures()
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun downloadFile(url: String): Observable<ResponseBody> {
        return restApi.downloadFile(url)
    }

    override fun downloadCounter(id: Int): Single<ObjectDownloadResponse> {
        return restApi.objectDownload(id)
    }

    override fun getMyTexturesList(): Single<List<MyTextures>> {
        return database.getMyTextures().toList()
    }

    override fun saveMyTexturesList(myTextures: MyTextures): Completable {
        return database.saveMyTextures(myTextures)
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTexturesList(pageNumber : Int,lang: Int): Single<TexturesResponse> {
        return restApi.getTextures(3, pageNumber,lang)
                .doOnSuccess {
                    Log.d("TEST1", it.toString())
                    it.objects?.forEachIndexed { _, textures ->
                        database.saveTextures(textures)
                                .andThen(database.getTextures())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ Log.d("TEST2", it.toString())},Throwable::printStackTrace) }

                }
                .doOnError {}
    }

    override fun getTexturesObjectType(): Single<ObjectTypesResponse> {
        return restApi.getObjectTypes(3)
    }

}