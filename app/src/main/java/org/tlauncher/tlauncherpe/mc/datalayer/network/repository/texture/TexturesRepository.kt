package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.texture

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MyTextures
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Textures
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectDownloadResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectTypesResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.TexturesResponse

interface TexturesRepository {

    fun getTexturesList(pageNumber : Int,lang: Int): Single<TexturesResponse>

    fun getTexturesListFromDb(): Observable<Textures>

    fun getTexturesObjectType(): Single<ObjectTypesResponse>

    fun downloadFile(url:String): Observable<ResponseBody>

    fun downloadCounter(id: Int): Single<ObjectDownloadResponse>

    fun getMyTexturesList(): Single<List<MyTextures>>

    fun saveMyTexturesList(myTextures: MyTextures): Completable
}