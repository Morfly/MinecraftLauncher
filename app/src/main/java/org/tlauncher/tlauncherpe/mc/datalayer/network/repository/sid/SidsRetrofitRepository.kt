package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.sid

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.ResponseBody
import org.tlauncher.tlauncherpe.mc.datalayer.db.DbManager
import org.tlauncher.tlauncherpe.mc.datalayer.network.RestApi
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MySids
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Sids
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectDownloadResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectTypesResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.SidsResponse
import javax.inject.Inject

class SidsRetrofitRepository
@Inject
constructor(private val restApi: RestApi, private val database: DbManager): SidsRepository{

    override fun getSidsList(pageNumber : Int,lang: Int): Single<SidsResponse> {
        return restApi.getSids(5,pageNumber,lang)
                .doOnSuccess {
                    Log.d("TEST1", it.toString())
                    it.objects?.forEachIndexed { _, sids ->
                        database.saveSids(sids)
                                .andThen(database.getSids())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ Log.d("TEST2", it.toString())},Throwable::printStackTrace) }

                }
                .doOnError {}
    }

    override fun getSidsObjectType(): Single<ObjectTypesResponse> {
        return restApi.getObjectTypes(2)
    }

    override fun getSidsListFromDb(): Observable<Sids> {
        return database.getSids()
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun downloadFile(url: String): Observable<ResponseBody> {
        return restApi.downloadFile(url)
    }

    override fun downloadCounter(id: Int): Single<ObjectDownloadResponse> {
        return restApi.objectDownload(id)
    }

    override fun getMySidsList(): Single<List<MySids>> {
        return database.getMySids().toList()
    }

    override fun saveMySidsList(mySids: MySids): Completable {
        return database.saveMySids(mySids)
                .observeOn(AndroidSchedulers.mainThread())
    }
}