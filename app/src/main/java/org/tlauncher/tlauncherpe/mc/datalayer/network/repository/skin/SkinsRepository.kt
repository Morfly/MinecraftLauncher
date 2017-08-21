package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.skin

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MySkins
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Skins
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectDownloadResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectTypesResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.SkinsResponse

interface SkinsRepository {

    fun getSkinsList(pageNumber : Int,lang: Int): Single<SkinsResponse>

    fun getSkinsListFromDb(): Observable<Skins>

    fun getSkinsObjectType(): Single<ObjectTypesResponse>

    fun downloadFile(url:String): Observable<ResponseBody>

    fun downloadCounter(id: Int): Single<ObjectDownloadResponse>

    fun getMySkinsList(): Single<List<MySkins>>

    fun saveMySkinsList(mySkins: MySkins): Completable
}