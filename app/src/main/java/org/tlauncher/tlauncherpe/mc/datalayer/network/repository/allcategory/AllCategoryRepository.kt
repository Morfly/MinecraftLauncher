package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.allcategory

import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.*
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.*

/**
 * Created by 85064 on 26.07.2017.
 */
interface AllCategoryRepository {

    fun getAddonsList(pageNumber : Int,lang: Int): Single<AddonsResponse>

    fun getAddonsListFromDb(): Observable<Addons>

    fun getMyAddonsList(): Single<List<MyAddons>>

    fun getMapsList(pageNumber : Int,lang: Int) : Single<ObjectsResponse>

    fun getMapsListFromDb() : Observable<Objects>

    fun getMyWorldsList() : Single<List<MyMods>>

    fun getSidsList(pageNumber : Int,lang: Int): Single<SidsResponse>

    fun getSidsListFromDb(): Observable<Sids>

    fun getMySidsList(): Single<List<MySids>>

    fun getSkinsList(pageNumber : Int,lang: Int): Single<SkinsResponse>

    fun getSkinsListFromDb(): Observable<Skins>

    fun getMySkinsList(): Single<List<MySkins>>

    fun getTexturesList(pageNumber : Int,lang: Int): Single<TexturesResponse>

    fun getTexturesListFromDb(): Observable<Textures>

    fun getMyTexturesList(): Single<List<MyTextures>>

    fun downloadFile(url:String): Observable<ResponseBody>

    fun downloadCounter(id: Int): Single<ObjectDownloadResponse>
}