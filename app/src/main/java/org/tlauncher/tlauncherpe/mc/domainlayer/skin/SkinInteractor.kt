package org.tlauncher.tlauncherpe.mc.domainlayer.skin

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
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MySkins
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.skin.SkinsRetrofitRepository
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectDownloadResponse
import org.tlauncher.tlauncherpe.mc.presentationlayer.skin.SkinItemViewModel
import java.util.*
import javax.inject.Inject

interface SkinInteractor{
    //counter for pages
    var currentPage : Int
    //load skin from server
    fun getSkinsList(lang: Int): Single<ArrayList<SkinItemViewModel>>
    //download file from server
    fun downloadFile(url:String): Observable<ResponseBody>
    //increment download counter
    fun downloadCounter(id: Int): Single<ObjectDownloadResponse>
    //get skin list from DB
    fun getMySkinsList(): Single<ArrayList<SkinItemViewModel>>?
    // save skin to my skin table in DB
    fun saveMySkinsList(mySkins: MySkins): Completable
    //get data list from server with check what data saved in my skin
    fun getListData(refresh: Boolean,lang: Int): Single<ArrayList<SkinItemViewModel>>
    //get skin list from DB
    fun getSkinLIstFromDb(): Single<ArrayList<SkinItemViewModel>>
    //get data list from DB with check what data saved in my skin
    fun getSkinLIstFromDbWithChack(): Single<ArrayList<SkinItemViewModel>>
    //remove item from my skin by id
    fun removeMySkinById(id: Int): Completable
    //get item from my skin by id
    fun getMySkinsById(id: Int): Observable<MySkins>
    //update item in my skin
    fun updateMyItem(mySkins: MySkins): Completable
}

@PerFragment
class SkinUseCase
@Inject
constructor(private var skinsRetrofitRepository: SkinsRetrofitRepository,
            private val database: DbManager,
            val preferencesManager: PreferencesManager): SkinInteractor {

    override var currentPage: Int = 1

    override fun getSkinsList(lang: Int): Single<ArrayList<SkinItemViewModel>> {
        return skinsRetrofitRepository.getSkinsList(currentPage, lang)
                .map {
                    val list : ArrayList<SkinItemViewModel> = ArrayList()
                    it.objects?.forEachIndexed { _, skins ->
                        list.add(SkinItemViewModel(skins.imgs,skins.name,
                                skins.downloads,skins.file_size?.toLong(),skins.id,
                                skins.date,skins.file_url,false,skins.text,skins.views,""
                                , skins.type, skins.category, false))
                    }
                    return@map list
                }
                .doOnSuccess { currentPage++ }
    }

    override fun downloadFile(url: String): Observable<ResponseBody> {
        return skinsRetrofitRepository.downloadFile(url)
    }

    override fun downloadCounter(id: Int): Single<ObjectDownloadResponse> {
        return skinsRetrofitRepository.downloadCounter(id)
    }

    override fun getMySkinsList(): Single<ArrayList<SkinItemViewModel>>? {
        return skinsRetrofitRepository.getMySkinsList()
                .map {
                    val list : ArrayList<SkinItemViewModel> = ArrayList()
                    it.forEachIndexed { _, mySkins ->
                        list.add(SkinItemViewModel(mySkins.imgs,mySkins.name,
                                mySkins.downloads,mySkins.file_size?.toLong(),mySkins.id,mySkins.date,
                                mySkins.file_url,true,mySkins.text,mySkins.views,mySkins.filePath,
                                mySkins.type, mySkins.category, mySkins.isImported))
                    }

                    return@map list
                }
    }

    override fun saveMySkinsList(mySkins: MySkins): Completable {
        return skinsRetrofitRepository.saveMySkinsList(mySkins)
                .doOnComplete { preferencesManager.setISLoadSkins(true) }
    }

    override fun getListData(refresh: Boolean,lang: Int): Single<ArrayList<SkinItemViewModel>> {
        var isL: Boolean = preferencesManager.getIsLoadSkins()
        if (refresh) currentPage = 1
        if (isL) {
            return Single.zip(getSkinsList(lang), getMySkinsList(), BiFunction { t1, t2 ->
                val list: ArrayList<SkinItemViewModel> = ArrayList()
                t1.forEachIndexed { index, skins ->
                    val my = t2.filter({ p -> p.id == t1[index].id })
                    if (my.size == 0) {
                        list.add(SkinItemViewModel(skins.image, skins.name,
                                skins.downloads, skins.fileSize, skins.id,
                                skins.date, skins.url, false,skins.text,skins.views,""
                                , skins.type, skins.category, false))
                    } else {
                        list.add(SkinItemViewModel(skins.image, skins.name,
                                skins.downloads, skins.fileSize, skins.id,
                                skins.date, skins.url, true,skins.text,skins.views,""
                                , skins.type, skins.category, my[0].isImported))
                    }
                }
                return@BiFunction list
            })
        }else{
            return skinsRetrofitRepository.getSkinsList(currentPage,lang)
                    .map {
                        val list : ArrayList<SkinItemViewModel> = ArrayList()
                        it.objects?.forEachIndexed { _, skins ->
                            list.add(SkinItemViewModel(skins.imgs,skins.name,
                                    skins.downloads,skins.file_size?.toLong(),skins.id,
                                    skins.date,skins.file_url,false,skins.text,skins.views,""
                                    , skins.type, skins.category, false))
                        }
                        return@map list
                    }
                    .doOnSuccess { currentPage++ }
        }
    }

    override fun getSkinLIstFromDb(): Single<ArrayList<SkinItemViewModel>> {
        return skinsRetrofitRepository.getSkinsListFromDb()
                .toList()
                .map {
                    val list : ArrayList<SkinItemViewModel> = ArrayList()
                    it.forEachIndexed { _, skins ->
                        list.add(SkinItemViewModel(skins.imgs,skins.name,
                                skins.downloads,skins.file_size?.toLong(),skins.id,
                                skins.date,skins.file_url,false,skins.text,skins.views,"",
                                skins.type, skins.category, false))

                    }

                    return@map list
                }
    }

    override fun getSkinLIstFromDbWithChack(): Single<ArrayList<SkinItemViewModel>> {
        return Single.zip(getSkinLIstFromDb(), getMySkinsList(), BiFunction { t1, t2 ->
            val list: ArrayList<SkinItemViewModel> = ArrayList()
            t1.forEachIndexed { index, skins ->
                val my = t2.filter({ p -> p.id == t1[index].id })
                if (my.size == 0) {
                    list.add(SkinItemViewModel(skins.image, skins.name,
                            skins.downloads, skins.fileSize, skins.id,
                            skins.date, skins.url, false,skins.text,skins.views,""
                            , skins.type, skins.category, false))
                } else {
                    list.add(SkinItemViewModel( my[0].image,  my[0].name,
                            my[0].downloads,  my[0].fileSize,  my[0].id,
                            my[0].date,  my[0].url, true, my[0].text, my[0].views,""
                            ,  my[0].type,  my[0].category, my[0].isImported))
                }
            }
            return@BiFunction list
        })
    }

    override fun removeMySkinById(id: Int): Completable {
        return database.removeMySkinById(id)
    }

    override fun getMySkinsById(id: Int): Observable<MySkins> {
        return database.getMySkinsById(id)
    }

    override fun updateMyItem(mySkins: MySkins): Completable {
        return database.getMySkinByImport()
                .flatMapCompletable {
                    it.isImported = false
                    val myTxt = it
                    return@flatMapCompletable database.saveMySkins(myTxt)
                            .andThen (database.saveMySkins(mySkins))
                }
                .doOnSubscribe {database.saveMySkins(mySkins)
                        .subscribe({ Log.e("save","success")},Throwable::printStackTrace)}
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.e("save","error") }
    }
}