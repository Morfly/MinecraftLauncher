package org.tlauncher.tlauncherpe.mc.domainlayer.sid

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import okhttp3.ResponseBody
import org.tlauncher.tlauncherpe.mc.datalayer.PreferencesManager
import org.tlauncher.tlauncherpe.mc.datalayer.db.DbManager
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MySids
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.sid.SidsRetrofitRepository
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectDownloadResponse
import org.tlauncher.tlauncherpe.mc.presentationlayer.sid.SidItemViewModel
import java.util.*
import javax.inject.Inject

interface SidInteractor{
    //counter for pages
    var currentPage : Int
    //load sid from server
    fun getSidsList(lang: Int): Single<ArrayList<SidItemViewModel>>
    //download file from server
    fun downloadFile(url:String): Observable<ResponseBody>
    //increment download counter
    fun downloadCounter(id: Int): Single<ObjectDownloadResponse>
    //get sid list from DB
    fun getMySidsList(): Single<ArrayList<SidItemViewModel>>?
    // save sid to my sid table in DB
    fun saveMySidsList(mySids: MySids): Completable
    //get data list from server with check what data saved in my sid
    fun getListData(refresh: Boolean,lang: Int): Single<ArrayList<SidItemViewModel>>
    //get sid list from DB
    fun getSidLIstFromDb(): Single<ArrayList<SidItemViewModel>>
    //get data list from DB with check what data saved in my sid
    fun getSidLIstFromDbWithChack(): Single<ArrayList<SidItemViewModel>>
    //remove item from my sid by id
    fun removeMySidById(id: Int): Completable
    //get item from my sid by id
    fun getMySidsById(id: Int): Observable<MySids>
}

@PerFragment
class SidUseCase
@Inject
constructor(private var sidsRetrofitRepository: SidsRetrofitRepository,
            private val database: DbManager,
            val preferencesManager: PreferencesManager): SidInteractor {

    override var currentPage: Int = 1

    override fun getSidsList(lang: Int): Single<ArrayList<SidItemViewModel>> {
        return sidsRetrofitRepository.getSidsList(currentPage,lang)
                .map {
                    val list : ArrayList<SidItemViewModel> = ArrayList()
                    it.objects?.forEachIndexed { _, sids ->
                        list.add(SidItemViewModel(sids.imgs,sids.name,
                                sids.downloads,sids.file_size?.toLong(),sids.id,
                                sids.date,sids.file_url,false,sids.text,sids.views,""
                                , sids.type, sids.category))
                    }
                    return@map list
                }
                .doOnSuccess { currentPage++ }
    }

    override fun downloadFile(url: String): Observable<ResponseBody> {
        return sidsRetrofitRepository.downloadFile(url)
    }

    override fun downloadCounter(id: Int): Single<ObjectDownloadResponse> {
        return sidsRetrofitRepository.downloadCounter(id)
    }

    override fun getMySidsList(): Single<ArrayList<SidItemViewModel>>? {
        return sidsRetrofitRepository.getMySidsList()
                .map {
                    val list : ArrayList<SidItemViewModel> = ArrayList()
                    it.forEachIndexed { _, mySids ->
                        list.add(SidItemViewModel(mySids.imgs,mySids.name,
                                mySids.downloads,mySids.file_size?.toLong(),mySids.id,mySids.date,
                                mySids.file_url,true,mySids.text,mySids.views,mySids.filePath,
                                mySids.type, mySids.category))
                    }

                    return@map list
                }
    }

    override fun saveMySidsList(mySids: MySids): Completable {
        return sidsRetrofitRepository.saveMySidsList(mySids)
                .doOnComplete { preferencesManager.setISLoadSkins(true) }
    }

    override fun getListData(refresh: Boolean,lang: Int): Single<ArrayList<SidItemViewModel>> {
        var isL: Boolean = preferencesManager.getIsLoadSids()
        if (refresh) currentPage = 1
        if (isL) {
            return Single.zip(getSidsList(lang), getMySidsList(), BiFunction { t1, t2 ->
                val list: ArrayList<SidItemViewModel> = ArrayList()
                t1.forEachIndexed { index, sids ->
                    val my = t2.filter({ p -> p.id == t1[index].id })
                    if (my.size == 0) {
                        list.add(SidItemViewModel(sids.image, sids.name,
                                sids.downloads, sids.fileSize, sids.id,
                                sids.date, sids.url, false,sids.text,sids.views,""
                                , sids.type, sids.category))
                    } else {
                        list.add(SidItemViewModel(sids.image, sids.name,
                                sids.downloads, sids.fileSize, sids.id,
                                sids.date, sids.url, true,sids.text,sids.views,""
                                , sids.type, sids.category))
                    }
                }
                return@BiFunction list
            })
        }else{
            return sidsRetrofitRepository.getSidsList(currentPage, lang)
                    .map {
                        val list : ArrayList<SidItemViewModel> = ArrayList()
                        it.objects?.forEachIndexed { _, sids ->
                            list.add(SidItemViewModel(sids.imgs,sids.name,
                                    sids.downloads,sids.file_size?.toLong(),sids.id,
                                    sids.date,sids.file_url,false,sids.text,sids.views,""
                                    , sids.type, sids.category))
                        }
                        return@map list
                    }
                    .doOnSuccess { currentPage++ }
        }
    }

    override fun getSidLIstFromDb(): Single<ArrayList<SidItemViewModel>> {
        return sidsRetrofitRepository.getSidsListFromDb()
                .toList()
                .map {
                    val list : ArrayList<SidItemViewModel> = ArrayList()
                    it.forEachIndexed { _, sids ->
                        list.add(SidItemViewModel(sids.imgs,sids.name,
                                sids.downloads,sids.file_size?.toLong(),sids.id,
                                sids.date,sids.file_url,false,sids.text,sids.views,"",
                                sids.type, sids.category))

                    }

                    return@map list
                }
    }

    override fun getSidLIstFromDbWithChack(): Single<ArrayList<SidItemViewModel>> {
        return Single.zip(getSidLIstFromDb(), getMySidsList(), BiFunction { t1, t2 ->
            val list: ArrayList<SidItemViewModel> = ArrayList()
            t1.forEachIndexed { index, sids ->
                val my = t2.filter({ p -> p.id == t1[index].id })
                if (my.size == 0) {
                    list.add(SidItemViewModel(sids.image, sids.name,
                            sids.downloads, sids.fileSize, sids.id,
                            sids.date, sids.url, false,sids.text,sids.views,""
                            , sids.type, sids.category))
                } else {
                    list.add(SidItemViewModel(sids.image, sids.name,
                            sids.downloads, sids.fileSize, sids.id,
                            sids.date, sids.url, true,sids.text,sids.views,""
                            , sids.type, sids.category))
                }
            }
            return@BiFunction list
        })
    }

    override fun removeMySidById(id: Int): Completable {
        return database.removeMySidById(id)
    }

    override fun getMySidsById(id: Int): Observable<MySids> {
        return database.getMySidById(id)
    }
}