package org.tlauncher.tlauncherpe.mc.domainlayer.addon

import android.util.Log
import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import okhttp3.ResponseBody
import org.tlauncher.tlauncherpe.mc.datalayer.PreferencesManager
import org.tlauncher.tlauncherpe.mc.datalayer.db.DbManager
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MyAddons
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.addon.AddonsRetrofitRepository
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectDownloadResponse
import org.tlauncher.tlauncherpe.mc.presentationlayer.addon.AddonItemViewModel
import java.util.*
import javax.inject.Inject

interface AddonInteractor{
    //counter for pages
    var currentPage : Int
    //load addon from server
    fun getAddonsList(lang: Int): Single<ArrayList<AddonItemViewModel>>
    //download file from server
    fun downloadFile(url:String): Observable<ResponseBody>
    //increment download counter
    fun downloadCounter(id: Int): Single<ObjectDownloadResponse>
    //get addon list from DB
    fun getMyAddonsList(): Single<ArrayList<AddonItemViewModel>>?
    // save addon to my addon table in DB
    fun saveMyAddonsList(myAddons: MyAddons): Completable
    //get data list from server with check what data saved in my addon
    fun getListData(refresh: Boolean,lang: Int): Single<ArrayList<AddonItemViewModel>>
    //get addon list from DB
    fun getAddonLIstFromDb(): Single<ArrayList<AddonItemViewModel>>
    //get data list from DB with check what data saved in my addon
    fun getAddonLIstFromDbWithChack(): Single<ArrayList<AddonItemViewModel>>
    //remove item from my addon by id
    fun removeMyAddonById(id: Int): Completable
    //get item from my addon by id
    fun getMyAddonsById(id: Int): Observable<MyAddons>
    //update item in my addon
    fun updateMyItem(myAddons: MyAddons): Completable
}

@PerFragment
class AddonUseCase
@Inject
constructor(private var addonsRetrofitRepository: AddonsRetrofitRepository,
            private val database: DbManager,
            val preferencesManager: PreferencesManager): AddonInteractor {

    override var currentPage = 1

    override fun getAddonsList(lang: Int): Single<ArrayList<AddonItemViewModel>> {
        return addonsRetrofitRepository.getAddonsList(currentPage, lang)
                .map {
                    val list : ArrayList<AddonItemViewModel> = ArrayList()
                    it.objects?.forEachIndexed { _, addons ->
                        list.add(AddonItemViewModel(addons.imgs,addons.name,
                                addons.downloads,addons.file_size?.toLong(),addons.id,
                                addons.date,addons.file_url,false,addons.text,addons.views,""
                                , addons.type, addons.category, false))
                    }
                    return@map list
                }
                .doOnSuccess { currentPage++ }
    }

    override fun downloadFile(url: String): Observable<ResponseBody> {
        return addonsRetrofitRepository.downloadFile(url)
    }

    override fun downloadCounter(id: Int): Single<ObjectDownloadResponse> {
        return addonsRetrofitRepository.downloadCounter(id)
    }

    override fun getMyAddonsList(): Single<ArrayList<AddonItemViewModel>>? {
        return addonsRetrofitRepository.getMyAddonsList()
                .map {
                    val list : ArrayList<AddonItemViewModel> = ArrayList()
                    it.forEachIndexed { _, myAddons ->
                        list.add(AddonItemViewModel(myAddons.imgs,myAddons.name,
                                myAddons.downloads,myAddons.file_size?.toLong()?: 0,myAddons.id,myAddons.date,
                                myAddons.file_url,true,myAddons.text,myAddons.views,myAddons.filePath,
                                myAddons.type, myAddons.category, myAddons.isImported))
                    }

                    return@map list
                }
    }

    override fun saveMyAddonsList(myAddons: MyAddons): Completable {
        return addonsRetrofitRepository.saveMyAddonsList(myAddons)
                .doOnComplete { preferencesManager.setISLoadAddons(true) }
    }


    override fun getListData(refresh: Boolean,lang: Int): Single<ArrayList<AddonItemViewModel>> {
        var isL: Boolean = preferencesManager.getIsLoadAddons()
        if (refresh) currentPage = 1
        if (isL) {
            return Single.zip(getAddonsList(lang), getMyAddonsList(), BiFunction { t1, t2 ->
                val list: ArrayList<AddonItemViewModel> = ArrayList()
                t1.forEachIndexed { index, addons ->
                    val my = t2.filter({ p -> p.id == t1[index].id })
                    if (my.size == 0) {
                        list.add(AddonItemViewModel(addons.image, addons.name,
                                addons.downloads, addons.fileSize, addons.id,
                                addons.date, addons.url, false,addons.text,addons.views,""
                                , addons.type, addons.category, false))
                    } else {
                        list.add(AddonItemViewModel(addons.image, addons.name,
                                addons.downloads, addons.fileSize, addons.id,
                                addons.date, addons.url, true,addons.text,addons.views,my[0].filePath
                                , addons.type, addons.category, my[0].isImported))
                    }
                }
                return@BiFunction list
            })
        }else{
            return addonsRetrofitRepository.getAddonsList(currentPage, lang)
                    .map {
                        val list : ArrayList<AddonItemViewModel> = ArrayList()
                        it.objects?.forEachIndexed { _, addons ->
                            list.add(AddonItemViewModel(addons.imgs,addons.name,
                                    addons.downloads,addons.file_size?.toLong(),addons.id,
                                    addons.date,addons.file_url,false,addons.text,addons.views,""
                                    , addons.type, addons.category, false))
                        }

                        return@map list
                    }
                    .doOnSuccess { currentPage++ }
        }
    }

    override fun getAddonLIstFromDb(): Single<ArrayList<AddonItemViewModel>> {
        return addonsRetrofitRepository.getAddonsListFromDb()
                .toList()
                .map {
                    val list : ArrayList<AddonItemViewModel> = ArrayList()
                    it.forEachIndexed { _, addons ->
                        list.add(AddonItemViewModel(addons.imgs,addons.name,
                                addons.downloads,addons.file_size?.toLong(),addons.id,
                                addons.date,addons.file_url,false,addons.text,addons.views,"",
                                addons.type, addons.category, false))

                    }

                    return@map list
                }
    }

    override fun getAddonLIstFromDbWithChack(): Single<ArrayList<AddonItemViewModel>> {
        return Single.zip(getAddonLIstFromDb(), getMyAddonsList(), BiFunction { t1, t2 ->
            val list: ArrayList<AddonItemViewModel> = ArrayList()
            t1.forEachIndexed { index, addons ->
                val my = t2.filter({ p -> p.id == t1[index].id })
                if (my.size == 0) {
                    list.add(AddonItemViewModel(addons.image, addons.name,
                            addons.downloads, addons.fileSize, addons.id,
                            addons.date, addons.url, false,addons.text,addons.views,""
                            , addons.type, addons.category, false))
                } else {
                    list.add(AddonItemViewModel(my[0].image, my[0].name,
                            my[0].downloads, my[0].fileSize, my[0].id,
                            my[0].date, my[0].url, true,my[0].text,my[0].views, my[0].filePath
                            , my[0].type, my[0].category, my[0].isImported))
                }
            }
            return@BiFunction list
        })
    }

    override fun removeMyAddonById(id: Int): Completable {
        return database.removeMyAddonById(id)
    }

    override fun getMyAddonsById(id: Int): Observable<MyAddons> {
        return database.getMyAddonById(id)
    }

    override fun updateMyItem(myAddons: MyAddons): Completable {
        return database.getMyAddonByImport()
                .flatMapCompletable {
                    it.isImported = false
                    val myTxt = it
                    return@flatMapCompletable database.saveMyAddons(myTxt)
                            .andThen (database.saveMyAddons(myAddons))
                }
                .doOnSubscribe {database.saveMyAddons(myAddons)
                        .subscribe({ Log.e("save","success")},Throwable::printStackTrace)}
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.e("save","error") }
    }
}