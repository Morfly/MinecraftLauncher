package org.tlauncher.tlauncherpe.mc.domainlayer.allcategory

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.tlauncher.tlauncherpe.mc.Constants
import org.tlauncher.tlauncherpe.mc.datalayer.PreferencesManager
import org.tlauncher.tlauncherpe.mc.datalayer.db.DbManager
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.allcategory.AllCategoryRetrofitRepository
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectDownloadResponse
import org.tlauncher.tlauncherpe.mc.presentationlayer.addon.AddonItemViewModel
import org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory.AllCategoryItemViewModel
import org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory.Data
import org.tlauncher.tlauncherpe.mc.presentationlayer.sid.SidItemViewModel
import org.tlauncher.tlauncherpe.mc.presentationlayer.skin.SkinItemViewModel
import org.tlauncher.tlauncherpe.mc.presentationlayer.texture.TextureItemViewModel
import org.tlauncher.tlauncherpe.mc.presentationlayer.world.WorldItemViewModel
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by 85064 on 26.07.2017.
 */
interface AllCategoryInteractor{
    //download file from server
    fun downloadFile(url:String): Observable<ResponseBody>
    //increment download counter
    fun downloadCounter(id: Int): Single<ObjectDownloadResponse>

    //get data list from server with check what data saved in my addon
    fun getAddonListData(refresh: Boolean,lang: Int): Single<ArrayList<AddonItemViewModel>>
    //get data list from DB with check what data saved in my addon
    fun getAddonLIstFromDbWithChack(): Single<ArrayList<AddonItemViewModel>>
    //get addon list from DB
    fun getMyAddonsList(): Single<ArrayList<AddonItemViewModel>>?
    //load addon from server
    fun getAddonsList(lang: Int): Single<ArrayList<AddonItemViewModel>>
    //get addon list from DB
    fun getAddonLIstFromDb(): Single<ArrayList<AddonItemViewModel>>

    //get data list from server with check what data saved in my sid
    fun getSidListData(refresh: Boolean,lang: Int): Single<ArrayList<SidItemViewModel>>
    //get sid list from DB
    fun getSidLIstFromDb(): Single<ArrayList<SidItemViewModel>>
    //get data list from DB with check what data saved in my sid
    fun getSidLIstFromDbWithChack(): Single<ArrayList<SidItemViewModel>>
    //get sid list from DB
    fun getMySidsList(): Single<ArrayList<SidItemViewModel>>?
    //load sid from server
    fun getSidsList(lang: Int): Single<ArrayList<SidItemViewModel>>

    //get data list from server with check what data saved in my skin
    fun getSkinListData(refresh: Boolean,lang: Int): Single<ArrayList<SkinItemViewModel>>
    //get skin list from DB
    fun getSkinLIstFromDb(): Single<ArrayList<SkinItemViewModel>>
    //get data list from DB with check what data saved in my skin
    fun getSkinLIstFromDbWithChack(): Single<ArrayList<SkinItemViewModel>>
    //get skin list from DB
    fun getMySkinsList(): Single<ArrayList<SkinItemViewModel>>?
    //load skin from server
    fun getSkinsList(lang: Int): Single<ArrayList<SkinItemViewModel>>

    //get data list from server with check what data saved in my textur
    fun getTextureListData(refresh: Boolean,lang: Int): Single<ArrayList<TextureItemViewModel>>
    //get textur list from DB
    fun getTextureLIstFromDb(): Single<ArrayList<TextureItemViewModel>>
    //get data list from DB with check what data saved in my textur
    fun getTextureLIstFromDbWithChack(): Single<ArrayList<TextureItemViewModel>>
    //get textur list from DB
    fun getMyTexturesList(): Single<ArrayList<TextureItemViewModel>>?
    //load textur from server
    fun getTexturesList(lang: Int): Single<ArrayList<TextureItemViewModel>>

    //get data list from server with check what data saved in my world
    fun getWorldListData(refresh: Boolean,lang: Int): Single<ArrayList<WorldItemViewModel>>
    //get world list from DB
    fun getWorldLIstFromDb(): Single<ArrayList<WorldItemViewModel>>
    //get data list from DB with check what data saved in my world
    fun getWorldLIstFromDbWithChack(): Single<ArrayList<WorldItemViewModel>>
    //get world list from DB
    fun getMyWorldsList(): Single<ArrayList<WorldItemViewModel>>?
    //load world from server
    fun getWorldsList(lang: Int): Single<ArrayList<WorldItemViewModel>>

    //get all lists of data
    fun getAllListsOfData(refresh: Boolean): Single<ArrayList<AllCategoryItemViewModel>>
}

@PerFragment
class AllCategoryUseCase
@Inject
constructor(private var allCategoryRetrofitRepository: AllCategoryRetrofitRepository,
            private val database: DbManager,
            val preferencesManager: PreferencesManager): AllCategoryInteractor {

    override fun getAddonListData(refresh: Boolean, lang: Int): Single<ArrayList<AddonItemViewModel>> {
        var isL: Boolean = preferencesManager.getIsLoadAddons()
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
            return allCategoryRetrofitRepository.getAddonsList(1, lang)
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
                    .doOnSuccess { }
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

    override fun getAddonLIstFromDb(): Single<ArrayList<AddonItemViewModel>> {
        return allCategoryRetrofitRepository.getAddonsListFromDb()
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

    override fun downloadFile(url: String): Observable<ResponseBody> {
        return allCategoryRetrofitRepository.downloadFile(url)
    }

    override fun downloadCounter(id: Int): Single<ObjectDownloadResponse> {
        return allCategoryRetrofitRepository.downloadCounter(id)
    }

    override fun getMyAddonsList(): Single<ArrayList<AddonItemViewModel>>? {
        return allCategoryRetrofitRepository.getMyAddonsList()
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

    override fun getAddonsList(lang: Int): Single<ArrayList<AddonItemViewModel>> {
        return allCategoryRetrofitRepository.getAddonsList(1, lang)
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
                .doOnSuccess {  }
    }

    override fun getSidListData(refresh: Boolean, lang: Int): Single<ArrayList<SidItemViewModel>> {
        var isL: Boolean = preferencesManager.getIsLoadSids()
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
            return allCategoryRetrofitRepository.getSidsList(1, lang)
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
                    .doOnSuccess {  }
        }
    }

    override fun getSidLIstFromDb(): Single<ArrayList<SidItemViewModel>> {
        return allCategoryRetrofitRepository.getSidsListFromDb()
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

    override fun getMySidsList(): Single<ArrayList<SidItemViewModel>>? {
        return allCategoryRetrofitRepository.getMySidsList()
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

    override fun getSidsList(lang: Int): Single<ArrayList<SidItemViewModel>> {
        return allCategoryRetrofitRepository.getSidsList(1,lang)
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
                .doOnSuccess {  }
    }

    override fun getSkinListData(refresh: Boolean, lang: Int): Single<ArrayList<SkinItemViewModel>> {
        var isL: Boolean = preferencesManager.getIsLoadSkins()
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
            return allCategoryRetrofitRepository.getSkinsList(1,lang)
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
                    .doOnSuccess {  }
        }
    }

    override fun getSkinLIstFromDb(): Single<ArrayList<SkinItemViewModel>> {
        return allCategoryRetrofitRepository.getSkinsListFromDb()
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

    override fun getMySkinsList(): Single<ArrayList<SkinItemViewModel>>? {
        return allCategoryRetrofitRepository.getMySkinsList()
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

    override fun getSkinsList(lang: Int): Single<ArrayList<SkinItemViewModel>> {
        return allCategoryRetrofitRepository.getSkinsList(1, lang)
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
                .doOnSuccess {  }
    }

    override fun getTextureListData(refresh: Boolean, lang: Int): Single<ArrayList<TextureItemViewModel>> {
        var isL: Boolean = preferencesManager.getIsLoadTextures()
        if (isL) {
            return Single.zip(getTexturesList(lang), getMyTexturesList(), BiFunction { t1, t2 ->
                val list: java.util.ArrayList<TextureItemViewModel> = java.util.ArrayList()
                t1.forEachIndexed { index, textures ->
                    val my = t2.filter({ p -> p.id == t1[index].id })
                    if (my.size == 0) {
                        list.add(TextureItemViewModel(textures.image, textures.name,
                                textures.downloads, textures.fileSize, textures.id,
                                textures.date, textures.url, false,textures.text,textures.views,""
                                , textures.type, textures.category, false))
                    } else {
                        list.add(TextureItemViewModel(textures.image, textures.name,
                                textures.downloads, textures.fileSize, textures.id,
                                textures.date, textures.url, true,textures.text,textures.views,my[0].filePath
                                , textures.type, textures.category, my[0].isImported))
                    }
                }
                return@BiFunction list
            })
        }else{
            return allCategoryRetrofitRepository.getTexturesList(1, lang)
                    .map {
                        val list : java.util.ArrayList<TextureItemViewModel> = java.util.ArrayList()
                        it.objects?.forEachIndexed { _, textures ->
                            list.add(TextureItemViewModel(textures.imgs,textures.name,
                                    textures.downloads,textures.file_size?.toLong(),textures.id,
                                    textures.date,textures.file_url,false,textures.text,textures.views,""
                                    , textures.type, textures.category, false))
                        }
                        return@map list
                    }
                    .doOnSuccess {  }
        }
    }

    override fun getTextureLIstFromDb(): Single<ArrayList<TextureItemViewModel>> {
        return allCategoryRetrofitRepository.getTexturesListFromDb()
                .toList()
                .map {
                    val list : java.util.ArrayList<TextureItemViewModel> = java.util.ArrayList()
                    it.forEachIndexed { _, textures ->
                        list.add(TextureItemViewModel(textures.imgs,textures.name,
                                textures.downloads,textures.file_size?.toLong(),textures.id,
                                textures.date,textures.file_url,false,textures.text,textures.views,"",
                                textures.type, textures.category, false))

                    }

                    return@map list
                }
    }

    override fun getTextureLIstFromDbWithChack(): Single<ArrayList<TextureItemViewModel>> {
        return Single.zip(getTextureLIstFromDb(), getMyTexturesList(), BiFunction { t1, t2 ->
            val list: java.util.ArrayList<TextureItemViewModel> = java.util.ArrayList()
            t1.forEachIndexed { index, textures ->
                val my = t2.filter({ p -> p.id == t1[index].id })
                if (my.size == 0) {
                    list.add(TextureItemViewModel(textures.image, textures.name,
                            textures.downloads, textures.fileSize, textures.id,
                            textures.date, textures.url, false,textures.text,textures.views,""
                            , textures.type, textures.category, false))
                } else {
                    list.add(TextureItemViewModel(my[0].image, my[0].name,
                            my[0].downloads, my[0].fileSize, my[0].id,
                            my[0].date, my[0].url, true,my[0].text,my[0].views, my[0].filePath
                            , my[0].type, my[0].category, my[0].isImported))
                }
            }
            return@BiFunction list
        })
    }

    override fun getMyTexturesList(): Single<ArrayList<TextureItemViewModel>>? {
        return allCategoryRetrofitRepository.getMyTexturesList()
                .map {
                    val list : java.util.ArrayList<TextureItemViewModel> = java.util.ArrayList()
                    it.forEachIndexed { _, myTextures ->
                        list.add(TextureItemViewModel(myTextures.imgs,myTextures.name,
                                myTextures.downloads,myTextures.file_size?.toLong(),myTextures.id,myTextures.date,
                                myTextures.file_url,true,myTextures.text,myTextures.views,myTextures.filePath,
                                myTextures.type, myTextures.category, myTextures.isImported))
                    }

                    return@map list
                }
    }

    override fun getTexturesList(lang: Int): Single<ArrayList<TextureItemViewModel>> {
        return allCategoryRetrofitRepository.getTexturesList(1,lang)
                .map {
                    val list : java.util.ArrayList<TextureItemViewModel> = java.util.ArrayList()
                    it.objects?.forEachIndexed { _, textures ->
                        list.add(TextureItemViewModel(textures.imgs,textures.name,
                                textures.downloads,textures.file_size?.toLong(),textures.id,
                                textures.date,textures.file_url,false,textures.text,textures.views,""
                                , textures.type, textures.category,false))
                    }
                    return@map list
                }
                .doOnSuccess {  }
    }

    override fun getWorldListData(refresh: Boolean, lang: Int): Single<ArrayList<WorldItemViewModel>> {
        var isL: Boolean = preferencesManager.getIsLoadMaps()
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
            return allCategoryRetrofitRepository.getMapsList(1, lang)
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
                    .doOnSuccess {  }
        }
    }

    override fun getWorldLIstFromDb(): Single<ArrayList<WorldItemViewModel>> {
        return allCategoryRetrofitRepository.getMapsListFromDb()
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

    override fun getMyWorldsList(): Single<ArrayList<WorldItemViewModel>>? {
        return allCategoryRetrofitRepository.getMyWorldsList()
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

    override fun getWorldsList(lang: Int): Single<ArrayList<WorldItemViewModel>> {
        return allCategoryRetrofitRepository.getMapsList(1, lang)
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
                .doOnSuccess {  }
    }

    override fun getAllListsOfData(refresh: Boolean): Single<ArrayList<AllCategoryItemViewModel>> {
        return Single.zip(getAddonListData(refresh,1),getTextureListData(refresh,1), BiFunction{ t1, t2 ->
            val list: ArrayList<AllCategoryItemViewModel> = arrayListOf()
            list.add(AllCategoryItemViewModel(Constants.HEADER,Constants.ADDON,null))
            var counter = 0
            var counter2 = 0
            t1.forEachIndexed { _, addonItemViewModel ->
                if (counter <4) {
                    val data: Data = Data()
                    data.id = addonItemViewModel.id
                    data.category = addonItemViewModel.category
                    data.date = addonItemViewModel.date
                    data.downloads = addonItemViewModel.downloads
                    data.filePath = addonItemViewModel.filePath
                    data.fileSize = addonItemViewModel.fileSize
                    data.image = addonItemViewModel.image
                    data.isImported = addonItemViewModel.isImported
                    data.loaded = addonItemViewModel.loaded
                    data.name = addonItemViewModel.name
                    data.text = addonItemViewModel.text
                    data.type = addonItemViewModel.type
                    data.url = addonItemViewModel.url
                    data.views = addonItemViewModel.views
                    list.add(AllCategoryItemViewModel(Constants.ITEMS, Constants.ADDON, data))
                    counter ++
                }
            }
            list.add(AllCategoryItemViewModel(Constants.HEADER,Constants.TEXTURE,null))
            t2.forEachIndexed { _, textureItemViewModel ->
                if (counter2 < 4) {
                    val data: Data = Data()
                    data.id = textureItemViewModel.id
                    data.category = textureItemViewModel.category
                    data.date = textureItemViewModel.date
                    data.downloads = textureItemViewModel.downloads
                    data.filePath = textureItemViewModel.filePath
                    data.fileSize = textureItemViewModel.fileSize
                    data.image = textureItemViewModel.image
                    data.isImported = textureItemViewModel.isImported
                    data.loaded = textureItemViewModel.loaded
                    data.name = textureItemViewModel.name
                    data.text = textureItemViewModel.text
                    data.type = textureItemViewModel.type
                    data.url = textureItemViewModel.url
                    data.views = textureItemViewModel.views
                    list.add(AllCategoryItemViewModel(Constants.ITEMS, Constants.TEXTURE, data))
                    counter2 ++
                }
            }
            return@BiFunction list
        })
        /*val list: ArrayList<AllCategoryItemViewModel> = arrayListOf()
        return getAddonListData(refresh, 1)
                .timeout(1,TimeUnit.MINUTES)
                .subscribeOn(Schedulers.io())
                .map {
                    list.add(AllCategoryItemViewModel(Constants.HEADER,Constants.ADDON,null))
                    it.forEachIndexed { index, addonItemViewModel ->
                        val data: Data = Data()
                        data.id = addonItemViewModel.id
                        data.category = addonItemViewModel.category
                        data.date = addonItemViewModel.date
                        data.downloads = addonItemViewModel.downloads
                        data.filePath = addonItemViewModel.filePath
                        data.fileSize = addonItemViewModel.fileSize
                        data.image = addonItemViewModel.image
                        data.isImported = addonItemViewModel.isImported
                        data.loaded = addonItemViewModel.loaded
                        data.name = addonItemViewModel.name
                        data.text = addonItemViewModel.text
                        data.type = addonItemViewModel.type
                        data.url = addonItemViewModel.url
                        data.views = addonItemViewModel.views
                        list.add(AllCategoryItemViewModel(Constants.ITEMS, Constants.ADDON, data))
                    }
                    return@map list
                }
                .flatMap {
                    getTextureListData(refresh,1)
                            .map{
                                list.add(AllCategoryItemViewModel(Constants.HEADER,Constants.TEXTURE,null))
                                it.forEachIndexed { index, textureItemViewModel ->
                                    val data: Data = Data()
                                    data.id = textureItemViewModel.id
                                    data.category = textureItemViewModel.category
                                    data.date = textureItemViewModel.date
                                    data.downloads = textureItemViewModel.downloads
                                    data.filePath = textureItemViewModel.filePath
                                    data.fileSize = textureItemViewModel.fileSize
                                    data.image = textureItemViewModel.image
                                    data.isImported = textureItemViewModel.isImported
                                    data.loaded = textureItemViewModel.loaded
                                    data.name = textureItemViewModel.name
                                    data.text = textureItemViewModel.text
                                    data.type = textureItemViewModel.type
                                    data.url = textureItemViewModel.url
                                    data.views = textureItemViewModel.views
                                    list.add(AllCategoryItemViewModel(Constants.ITEMS, Constants.TEXTURE, data))
                                }
                                return@map list
                            }
                }
                .flatMap {
                    getWorldListData(refresh,1)
                            .map {
                                list.add(AllCategoryItemViewModel(Constants.HEADER,Constants.WORLD,null))
                                it.forEachIndexed { index, worldItemViewModel ->
                                    val data: Data = Data()
                                    data.id = worldItemViewModel.id
                                    data.category = worldItemViewModel.category
                                    data.date = worldItemViewModel.date
                                    data.downloads = worldItemViewModel.downloads
                                    data.filePath = worldItemViewModel.filePath
                                    data.fileSize = worldItemViewModel.fileSize
                                    data.image = worldItemViewModel.image
                                    data.loaded = worldItemViewModel.loaded
                                    data.name = worldItemViewModel.name
                                    data.text = worldItemViewModel.text
                                    data.type = worldItemViewModel.type
                                    data.url = worldItemViewModel.url
                                    data.views = worldItemViewModel.views
                                    list.add(AllCategoryItemViewModel(Constants.ITEMS, Constants.WORLD, data))
                                }
                                return@map list
                            }
                }
                .flatMap {
                    getSkinListData(refresh,1)
                            .map {
                                list.add(AllCategoryItemViewModel(Constants.HEADER,Constants.SKIN,null))
                                it.forEachIndexed { index, skinItemViewModel ->
                                    val data: Data = Data()
                                    data.id = skinItemViewModel.id
                                    data.category = skinItemViewModel.category
                                    data.date = skinItemViewModel.date
                                    data.downloads = skinItemViewModel.downloads
                                    data.filePath = skinItemViewModel.filePath
                                    data.fileSize = skinItemViewModel.fileSize
                                    data.image = skinItemViewModel.image
                                    data.isImported = skinItemViewModel.isImported
                                    data.loaded = skinItemViewModel.loaded
                                    data.name = skinItemViewModel.name
                                    data.text = skinItemViewModel.text
                                    data.type = skinItemViewModel.type
                                    data.url = skinItemViewModel.url
                                    data.views = skinItemViewModel.views
                                    list.add(AllCategoryItemViewModel(Constants.ITEMS, Constants.SKIN, data))
                                }
                                return@map list
                            }
                }
                .flatMap {
                    getSidListData(refresh,1)
                            .map {
                                list.add(AllCategoryItemViewModel(Constants.HEADER,Constants.SID,null))
                                it.forEachIndexed { index, sidItemViewModel ->
                                    val data: Data = Data()
                                    data.id = sidItemViewModel.id
                                    data.category = sidItemViewModel.category
                                    data.date = sidItemViewModel.date
                                    data.downloads = sidItemViewModel.downloads
                                    data.filePath = sidItemViewModel.filePath
                                    data.fileSize = sidItemViewModel.fileSize
                                    data.image = sidItemViewModel.image
                                    data.loaded = sidItemViewModel.loaded
                                    data.name = sidItemViewModel.name
                                    data.text = sidItemViewModel.text
                                    data.type = sidItemViewModel.type
                                    data.url = sidItemViewModel.url
                                    data.views = sidItemViewModel.views
                                    list.add(AllCategoryItemViewModel(Constants.ITEMS, Constants.SID, data))
                                }
                                return@map list
                            }
                }*/
    }
}