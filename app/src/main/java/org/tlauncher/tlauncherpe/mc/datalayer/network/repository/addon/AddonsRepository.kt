package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.addon

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Addons
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MyAddons
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.AddonsResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectDownloadResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectTypesResponse

interface AddonsRepository {

    fun getAddonsList(pageNumber : Int,lang: Int): Single<AddonsResponse>

    fun getAddonsListFromDb(): Observable<Addons>

    fun getAddonsObjectType(): Single<ObjectTypesResponse>

    fun downloadFile(url:String): Observable<ResponseBody>

    fun downloadCounter(id: Int): Single<ObjectDownloadResponse>

    fun getMyAddonsList(): Single<List<MyAddons>>

    fun saveMyAddonsList(myAddons: MyAddons): Completable
}