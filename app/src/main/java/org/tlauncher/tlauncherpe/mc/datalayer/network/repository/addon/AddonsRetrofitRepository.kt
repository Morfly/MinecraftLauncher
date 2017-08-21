package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.addon

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.ResponseBody
import org.tlauncher.tlauncherpe.mc.datalayer.db.DbManager
import org.tlauncher.tlauncherpe.mc.datalayer.network.RestApi
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Addons
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MyAddons
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.AddonsResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectDownloadResponse
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectTypesResponse
import javax.inject.Inject


class AddonsRetrofitRepository
@Inject
constructor(private val restApi: RestApi, private val database: DbManager): AddonsRepository{

    override fun getAddonsObjectType(): Single<ObjectTypesResponse> {
        return restApi.getObjectTypes(4)
    }

    override fun getAddonsList(pageNumber : Int,lang: Int): Single<AddonsResponse> {
        return restApi.getAddons(4,pageNumber,lang)
                .doOnSuccess {
                    Log.d("TEST1", it.toString())
                    it.objects?.forEachIndexed { _, addons ->
                        database.saveAddons(addons)
                                .andThen(database.getObjects())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ Log.d("TEST2", it.toString())},Throwable::printStackTrace) }

                }
                .doOnError {}
    }

    override fun getAddonsListFromDb(): Observable<Addons> {
        return database.getAddons()
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun downloadFile(url: String): Observable<ResponseBody> {
        return restApi.downloadFile(url)
    }

    override fun downloadCounter(id: Int): Single<ObjectDownloadResponse> {
        return restApi.objectDownload(id)
    }

    override fun getMyAddonsList(): Single<List<MyAddons>> {
        return database.getMyAddons().toList()
    }

    override fun saveMyAddonsList(myAddons: MyAddons): Completable {
        return database.saveMyAddons(myAddons)
                .observeOn(AndroidSchedulers.mainThread())
    }
}