package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.sid

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MySids
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Sids
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectDownloadResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectTypesResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.SidsResponse

interface SidsRepository {

    fun getSidsList(pageNumber : Int,lang: Int): Single<SidsResponse>

    fun getSidsListFromDb(): Observable<Sids>

    fun getSidsObjectType(): Single<ObjectTypesResponse>

    fun downloadFile(url:String): Observable<ResponseBody>

    fun downloadCounter(id: Int): Single<ObjectDownloadResponse>

    fun getMySidsList(): Single<List<MySids>>

    fun saveMySidsList(mySids: MySids): Completable
}