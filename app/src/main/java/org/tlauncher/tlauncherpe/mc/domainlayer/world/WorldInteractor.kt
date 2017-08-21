package org.tlauncher.tlauncherpe.mc.domainlayer.world

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import okhttp3.ResponseBody
import org.tlauncher.tlauncherpe.mc.datalayer.PreferencesManager
import org.tlauncher.tlauncherpe.mc.datalayer.db.DbManager
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MyMods
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.map.MapsRetrofitRepository
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectDownloadResponse
import org.tlauncher.tlauncherpe.mc.presentationlayer.world.WorldItemViewModel
import java.util.*
import javax.inject.Inject



interface WorldInteractor{
    //counter for pages
    var currentPage : Int
    //load world from server
    fun getWorldsList(lang: Int): Single<ArrayList<WorldItemViewModel>>
    //download file from server
    fun downloadFile(url:String): Observable<ResponseBody>
    //increment download counter
    fun downloadCounter(id: Int): Single<ObjectDownloadResponse>
    //get world list from DB
    fun getMyWorldsList(): Single<ArrayList<WorldItemViewModel>>?
    // save world to my world table in DB
    fun saveMyWorldsList(myMods: MyMods): Completable
    //get data list from server with check what data saved in my world
    fun getListData(refresh: Boolean,lang: Int): Single<ArrayList<WorldItemViewModel>>
    //get world list from DB
    fun getWorldLIstFromDb(): Single<ArrayList<WorldItemViewModel>>
    //get data list from DB with check what data saved in my world
    fun getWorldLIstFromDbWithChack(): Single<ArrayList<WorldItemViewModel>>
    //remove item from my world by id
    fun removeMyModById(id: Int): Completable
    //get item from my world by id
    fun getMyModsById(id: Int): Observable<MyMods>
}

@PerFragment
class WorldUseCase
@Inject
constructor(private var mapsRetrofitRepository: MapsRetrofitRepository,
            private val database: DbManager,
            val preferencesManager: PreferencesManager): WorldInteractor {

    override fun saveMyWorldsList(myMods: MyMods): Completable {
        return mapsRetrofitRepository.saveMyWorldsList(myMods)
                .doOnComplete { preferencesManager.setISLoadMaps(true) }
    }

    override fun getMyWorldsList(): Single<ArrayList<WorldItemViewModel>>? {
        return mapsRetrofitRepository.getMyWorldsList()
                .map {
                    val list : ArrayList<WorldItemViewModel> = ArrayList()
                        it.forEachIndexed { _, myMods ->
                            list.add(WorldItemViewModel(myMods.imgs,myMods.name,
                                    myMods.downloads,myMods.file_size?.toLong(),myMods.id,myMods.date,
                                    myMods.file_url,true,myMods.text,myMods.views,myMods.filePath,
                                    myMods.type, myMods.category))
                        }

                    return@map list
                }
    }

    override fun downloadCounter(id: Int): Single<ObjectDownloadResponse> {
        return mapsRetrofitRepository.downloadCounter(id)
    }

    override fun downloadFile(url: String): Observable<ResponseBody> {
        return mapsRetrofitRepository.downloadFile(url)
    }

    override var currentPage = 1

    override fun getWorldsList(lang: Int): Single<ArrayList<WorldItemViewModel>> {
        return mapsRetrofitRepository.getMapsList(currentPage, lang)
                .map {
                    val list : ArrayList<WorldItemViewModel> = ArrayList()
                    it.objects?.forEachIndexed { _, objects ->
                        list.add(WorldItemViewModel(objects.imgs,objects.name,
                                objects.downloads,objects.file_size?.toLong(),objects.id,
                                objects.date,objects.file_url,false,objects.text,objects.views,""
                                , objects.type, objects.category))
                    }
                    return@map list
                }
                .doOnSuccess { currentPage++ }
    }

    override fun getWorldLIstFromDb(): Single<ArrayList<WorldItemViewModel>> {
        return mapsRetrofitRepository.getMapsListFromDb()
                .toList()
                .map {
                    val list : ArrayList<WorldItemViewModel> = ArrayList()
                    it.forEachIndexed { _, objects ->
                        list.add(WorldItemViewModel(objects.imgs,objects.name,
                                objects.downloads,objects.file_size?.toLong(),objects.id,
                                objects.date,objects.file_url,false,objects.text,objects.views,"",
                                objects.type, objects.category))

                    }

                    return@map list
                }
    }

    override fun getWorldLIstFromDbWithChack(): Single<ArrayList<WorldItemViewModel>> {
        return Single.zip(getWorldLIstFromDb(), getMyWorldsList(), BiFunction { t1, t2 ->
            val list: ArrayList<WorldItemViewModel> = ArrayList()
            t1.forEachIndexed { index, objects ->
                val my = t2.filter({ p -> p.id == t1[index].id })
                if (my.size == 0) {
                    list.add(WorldItemViewModel(objects.image, objects.name,
                            objects.downloads, objects.fileSize, objects.id,
                            objects.date, objects.url, false,objects.text,objects.views,""
                            , objects.type, objects.category))
                } else {
                    list.add(WorldItemViewModel(objects.image, objects.name,
                            objects.downloads, objects.fileSize, objects.id,
                            objects.date, objects.url, true,objects.text,objects.views,""
                            , objects.type, objects.category))
                }
            }
            return@BiFunction list
        })
    }

    override fun getListData(refresh: Boolean,lang: Int): Single<ArrayList<WorldItemViewModel>> {
        var isL: Boolean = preferencesManager.getIsLoadMaps()
        if (refresh) currentPage = 1
        if (isL) {
            return Single.zip(getWorldsList(lang), getMyWorldsList(), BiFunction { t1, t2 ->
                val list: ArrayList<WorldItemViewModel> = ArrayList()
                t1.forEachIndexed { index, objects ->
                    val my = t2.filter({ p -> p.id == t1[index].id })
                    if (my.size == 0) {
                        list.add(WorldItemViewModel(objects.image, objects.name,
                                objects.downloads, objects.fileSize, objects.id,
                                objects.date, objects.url, false,objects.text,objects.views,""
                                , objects.type, objects.category))
                    } else {
                        list.add(WorldItemViewModel(objects.image, objects.name,
                                objects.downloads, objects.fileSize, objects.id,
                                objects.date, objects.url, true,objects.text,objects.views,""
                                , objects.type, objects.category))
                    }
                }
                return@BiFunction list
            })
        }else{
            return mapsRetrofitRepository.getMapsList(currentPage, lang)
                    .map {
                        val list : ArrayList<WorldItemViewModel> = ArrayList()
                        it.objects?.forEachIndexed { _, objects ->
                            list.add(WorldItemViewModel(objects.imgs,objects.name,
                                    objects.downloads,objects.file_size?.toLong(),objects.id,
                                    objects.date,objects.file_url,false,objects.text,objects.views,""
                                    , objects.type, objects.category))
                        }
                        return@map list
                    }
                    .doOnSuccess { currentPage++ }
        }
    }

    override fun removeMyModById(id: Int): Completable {
        return database.removeMyModById(id)
    }

    override fun getMyModsById(id: Int): Observable<MyMods> {
        return database.getMyModsById(id)
    }
}
