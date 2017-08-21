package org.tlauncher.tlauncherpe.mc.domainlayer.texture

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
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MyTextures
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.texture.TexturesRetrofitRepository
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.ObjectDownloadResponse
import org.tlauncher.tlauncherpe.mc.presentationlayer.texture.TextureItemViewModel
import javax.inject.Inject

interface TextureInteractor{
    //counter for pages
    var currentPage : Int
    //load textur from server
    fun getTexturesList(lang: Int): Single<ArrayList<TextureItemViewModel>>
    //download file from server
    fun downloadFile(url:String): Observable<ResponseBody>
    //increment download counter
    fun downloadCounter(id: Int): Single<ObjectDownloadResponse>
    //get textur list from DB
    fun getMyTexturesList(): Single<ArrayList<TextureItemViewModel>>?
    // save textur to my textur table in DB
    fun saveMyTexturesList(myTextures: MyTextures): Completable
    //get data list from server with check what data saved in my textur
    fun getListData(refresh: Boolean,lang: Int): Single<ArrayList<TextureItemViewModel>>
    //get textur list from DB
    fun getTextureLIstFromDb(): Single<ArrayList<TextureItemViewModel>>
    //get data list from DB with check what data saved in my textur
    fun getTextureLIstFromDbWithChack(): Single<ArrayList<TextureItemViewModel>>
    //remove item from my textur by id
    fun removeMyTextureById(id: Int): Completable
    //get item from my textur by id
    fun getMyTexturesById(id: Int): Observable<MyTextures>
    //update item in my textur
    fun updateMyItem(myTextures: MyTextures): Completable
}

@PerFragment
class TextureUseCase
@Inject
constructor(private var texturesRetrofitRepository: TexturesRetrofitRepository,
            private val database: DbManager,
            val preferencesManager: PreferencesManager): TextureInteractor {

    override var currentPage = 1

    override fun getTexturesList(lang: Int): Single<ArrayList<TextureItemViewModel>> {
        return texturesRetrofitRepository.getTexturesList(currentPage,lang)
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
                .doOnSuccess { currentPage++ }
    }

    override fun downloadFile(url: String): Observable<ResponseBody> {
        return texturesRetrofitRepository.downloadFile(url)
    }

    override fun downloadCounter(id: Int): Single<ObjectDownloadResponse> {
        return texturesRetrofitRepository.downloadCounter(id)
    }

    override fun getMyTexturesList(): Single<ArrayList<TextureItemViewModel>>? {
        return texturesRetrofitRepository.getMyTexturesList()
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

    override fun saveMyTexturesList(myTextures: MyTextures): Completable {
        return texturesRetrofitRepository.saveMyTexturesList(myTextures)
                .doOnComplete { preferencesManager.setISLoadTextures(true) }
    }

    override fun getListData(refresh: Boolean,lang: Int): Single<ArrayList<TextureItemViewModel>> {
        var isL: Boolean = preferencesManager.getIsLoadTextures()
        if (refresh) currentPage = 1
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
            return texturesRetrofitRepository.getTexturesList(currentPage, lang)
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
                    .doOnSuccess { currentPage++ }
        }
    }

    override fun getTextureLIstFromDb(): Single<ArrayList<TextureItemViewModel>> {
        return texturesRetrofitRepository.getTexturesListFromDb()
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

    override fun removeMyTextureById(id: Int): Completable {
        return database.removeMyTextureById(id)
    }

    override fun getMyTexturesById(id: Int): Observable<MyTextures> {
        return database.getMyTextureById(id)
    }

    override fun updateMyItem(myTextures: MyTextures): Completable {
        return database.getMyTextureByImport()
                .flatMapCompletable {
                    it.isImported = false
                    val myTxt = it
                    return@flatMapCompletable database.saveMyTextures(myTxt)
                            .andThen (database.saveMyTextures(myTextures))
                }
                .doOnSubscribe {database.saveMyTextures(myTextures)
                        .subscribe({Log.e("save","success")},Throwable::printStackTrace)}
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.e("save","error") }
    }
}