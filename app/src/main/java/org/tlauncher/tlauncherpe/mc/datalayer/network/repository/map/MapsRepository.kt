package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.map

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MyMods
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Objects
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectDownloadResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectTypesResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectsResponse

interface MapsRepository {

    fun getMapsList(pageNumber : Int,lang: Int) : Single<ObjectsResponse>

    fun getMapsListFromDb() : Observable<Objects>

    fun getMapsObjectType() : Single<ObjectTypesResponse>

    fun downloadFile(url : String) : Observable<ResponseBody>

    fun downloadCounter(id : Int) : Single<ObjectDownloadResponse>

    fun getMyWorldsList() : Single<List<MyMods>>

    fun saveMyWorldsList(myMods : MyMods) : Completable
}