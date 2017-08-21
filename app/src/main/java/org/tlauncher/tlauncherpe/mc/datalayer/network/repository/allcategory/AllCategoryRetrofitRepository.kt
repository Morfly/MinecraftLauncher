package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.allcategory

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.ResponseBody
import org.tlauncher.tlauncherpe.mc.datalayer.db.DbManager
import org.tlauncher.tlauncherpe.mc.datalayer.network.RestApi
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.*
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.addon.AddonsRepository
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.*
import javax.inject.Inject

/**
 * Created by 85064 on 26.07.2017.
 */
class AllCategoryRetrofitRepository
@Inject
constructor(private val restApi: RestApi, private val database: DbManager): AllCategoryRepository {

    override fun getAddonsList(pageNumber: Int, lang: Int): Single<AddonsResponse> {
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

    override fun getMyAddonsList(): Single<List<MyAddons>> {
        return database.getMyAddons().toList()
    }

    override fun getMapsList(pageNumber: Int, lang: Int): Single<ObjectsResponse> {
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

    override fun getMapsListFromDb(): Observable<Objects> {
        return database.getObjects()
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMyWorldsList(): Single<List<MyMods>> {
        return database.getMyMods().toList()
    }

    override fun getSidsList(pageNumber: Int, lang: Int): Single<SidsResponse> {
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

    override fun getSidsListFromDb(): Observable<Sids> {
        return database.getSids()
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMySidsList(): Single<List<MySids>> {
        return database.getMySids().toList()
    }

    override fun getSkinsList(pageNumber: Int, lang: Int): Single<SkinsResponse> {
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

    override fun getSkinsListFromDb(): Observable<Skins> {
        return database.getSkins()
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMySkinsList(): Single<List<MySkins>> {
        return database.getMySkins().toList()
    }

    override fun getTexturesList(pageNumber: Int, lang: Int): Single<TexturesResponse> {
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

    override fun getTexturesListFromDb(): Observable<Textures> {
        return database.getTextures()
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMyTexturesList(): Single<List<MyTextures>> {
        return database.getMyTextures().toList()
    }

    override fun downloadFile(url: String): Observable<ResponseBody> {
        return restApi.downloadFile(url)
    }

    override fun downloadCounter(id: Int): Single<ObjectDownloadResponse> {
        return restApi.objectDownload(id)
    }

}