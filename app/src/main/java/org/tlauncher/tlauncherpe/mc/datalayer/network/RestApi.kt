package org.tlauncher.tlauncherpe.mc.datalayer.network

import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.*
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface RestApi{

    /**
     * На данной старнице приведено описание запросов и ответов сервера для просмотра списка доступных объектов.
    Возможные ID типов

    1 - Карта
    2 - Скин
    3 - Текстура
    4 - Аддон
    5 - Сид
     */
    @GET("api/objects?type=&page=&lang=")
    fun getObjects(@Query("type") type : Int, @Query("page") page: Int, @Query("lang") lang: Int) : Single<ObjectsResponse>

    @GET("api/objects?type=&page=&lang=")
    fun getAddons(@Query("type") type : Int, @Query("page") page: Int, @Query("lang") lang: Int) : Single<AddonsResponse>

    @GET("api/objects?type=&page=&lang=")
    fun getTextures(@Query("type") type : Int, @Query("page") page: Int, @Query("lang") lang: Int) : Single<TexturesResponse>

    @GET("api/objects?type=&page=&lang=")
    fun getSkins(@Query("type") type : Int, @Query("page") page: Int, @Query("lang") lang: Int) : Single<SkinsResponse>

    @GET("api/objects?type=&page=&lang=")
    fun getSids(@Query("type") type : Int, @Query("page") page: Int, @Query("lang") lang: Int) : Single<SidsResponse>

    /**
     * На данной старнице приведено описание запросов и ответов сервера для просмотра версий игр
     */
    @GET("api/game_versions?")
    fun getGameVersions(): Single<GameVersionsResponse>

    /**
     * На данной старнице приведено описание запросов и ответов сервера для просмотра списка доступных сереров
     */
    @GET("api/game_servers")
    fun getGameServers(): Single<GameServersResponse>

    /**
     * На данной старнице приведено описание запросов и ответов сервера для просмотра списка доступных объектов
     */
    @GET("api/object_types?type=")
    fun getObjectTypes(@Query("type") type : Int): Single<ObjectTypesResponse>

    /**
     * На данной старнице приведено описание запросов и ответов сервера для обновления данных поля скачиваний объекта.
     * Если error 0 - значит операция завершена успехом.
     */
    @GET("api/object_download?id=")
    fun objectDownload(@Query("id") id: Int): Single<ObjectDownloadResponse>

    /**
     * На данной старнице приведено описание запросов и ответов сервера для обновления данных поля просмотра объекта.
     * Если error 0 - значит операция завершена успехом.
     */
    @GET("api/object_view?id=")
    fun objectView(@Query("id") id: Int): Single<ObjectDownloadResponse>

    @Streaming
    @GET
    fun downloadFile(@Url fileIrl: String): Observable<ResponseBody>

    @GET("api/push_apps?uid=")
    fun saveToken(@Query("uid") uid: String): Observable<SaveTokenResponse>
}
