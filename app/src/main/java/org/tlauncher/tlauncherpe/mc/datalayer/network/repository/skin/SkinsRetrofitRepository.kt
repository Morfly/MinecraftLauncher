package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.skin

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.ResponseBody
import org.tlauncher.tlauncherpe.mc.datalayer.db.DbManager
import org.tlauncher.tlauncherpe.mc.datalayer.network.RestApi
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MySkins
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Skins
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectDownloadResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectTypesResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.SkinsResponse
import javax.inject.Inject


class SkinsRetrofitRepository
@Inject
constructor(private val restApi: RestApi, private val database: DbManager): SkinsRepository{

    override fun getSkinsListFromDb(): Observable<Skins> {
        return database.getSkins()
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun downloadFile(url: String): Observable<ResponseBody> {
        return restApi.downloadFile(url)
    }

    override fun downloadCounter(id: Int): Single<ObjectDownloadResponse> {
        return restApi.objectDownload(id)
    }

    override fun getMySkinsList(): Single<List<MySkins>> {
        return database.getMySkins().toList()
    }

    override fun saveMySkinsList(mySkins: MySkins): Completable {
        return database.saveMySkins(mySkins)
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSkinsList(pageNumber : Int,lang: Int): Single<SkinsResponse> {
        return restApi.getSkins(2,pageNumber, lang)
                .doOnSuccess {
                    Log.d("TEST1", it.toString())
                    it.objects?.forEachIndexed { _, skins ->
                        database.saveSkins(skins)
                                .andThen(database.getObjects())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ Log.d("TEST2", it.toString())},Throwable::printStackTrace) }

                }
                .doOnError {}
    }

    override fun getSkinsObjectType(): Single<ObjectTypesResponse> {
       return restApi.getObjectTypes(2)
    }

}