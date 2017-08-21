package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.map

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.ResponseBody
import org.tlauncher.tlauncherpe.mc.datalayer.db.DbManager
import org.tlauncher.tlauncherpe.mc.datalayer.network.RestApi
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MyMods
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Objects
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectDownloadResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectTypesResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectsResponse
import javax.inject.Inject

class MapsRetrofitRepository
@Inject
constructor(private val restApi : RestApi, private val database : DbManager) : MapsRepository {

    override fun getMapsListFromDb() : Observable<Objects> {
        return database.getObjects()
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun saveMyWorldsList(myMods : MyMods) : Completable {
        return database.saveMyMods(myMods)
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMyWorldsList() : Single<List<MyMods>> {
        return database.getMyMods().toList()
    }

    override fun downloadCounter(id : Int) : Single<ObjectDownloadResponse> {
        return restApi.objectDownload(id)
    }

    override fun downloadFile(url : String) : Observable<ResponseBody> {
        return restApi.downloadFile(url)
    }

    override fun getMapsList(pageNumber : Int,lang: Int) : Single<ObjectsResponse> {
        return restApi.getObjects(1, pageNumber,lang)
                .doOnSuccess {
                    Log.d("TEST1", it.toString())
                    it.objects?.forEachIndexed { _, objects ->
                        database.saveObjects(objects)
                                .andThen(database.getObjects())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ Log.d("TEST2", it.toString()) }, Throwable::printStackTrace)
                    }
                }
                .doOnError {}
    }

    override fun getMapsObjectType() : Single<ObjectTypesResponse> {
        return restApi.getObjectTypes(1)
    }
}