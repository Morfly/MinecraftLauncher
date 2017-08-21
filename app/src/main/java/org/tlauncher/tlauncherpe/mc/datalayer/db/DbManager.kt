package org.tlauncher.tlauncherpe.mc.datalayer.db

import io.reactivex.Completable
import io.reactivex.Observable
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.*


interface DbManager {

    fun close()
    fun getObjects(): Observable<Objects>
    fun saveObjects(objects: Objects): Completable
    fun getMyMods(): Observable<MyMods>
    fun saveMyMods(myMods: MyMods): Completable
    fun getMyModsById(id: Int): Observable<MyMods>
    fun removeMyModById(id: Int): Completable

    fun getSkins(): Observable<Skins>
    fun saveSkins(skins: Skins): Completable
    fun getMySkins(): Observable<MySkins>
    fun saveMySkins(mySkins: MySkins): Completable
    fun getMySkinsById(id: Int): Observable<MySkins>
    fun removeMySkinById(id: Int): Completable
    fun getMySkinByImport(): Observable<MySkins>

    fun getTextures(): Observable<Textures>
    fun saveTextures(textures: Textures): Completable
    fun getMyTextures(): Observable<MyTextures>
    fun saveMyTextures(myTextures: MyTextures): Completable
    fun getMyTextureById(id: Int): Observable<MyTextures>
    fun removeMyTextureById(id: Int): Completable
    fun getMyTextureByImport(): Observable<MyTextures>

    fun getAddons(): Observable<Addons>
    fun saveAddons(addons: Addons): Completable
    fun getMyAddons(): Observable<MyAddons>
    fun saveMyAddons(myAddons: MyAddons): Completable
    fun getMyAddonById(id: Int): Observable<MyAddons>
    fun removeMyAddonById(id: Int): Completable
    fun getMyAddonByImport(): Observable<MyAddons>

    fun getSids(): Observable<Sids>
    fun saveSids(sids: Sids): Completable
    fun getMySids(): Observable<MySids>
    fun saveMySids(mySids: MySids): Completable
    fun getMySidById(id: Int): Observable<MySids>
    fun removeMySidById(id: Int): Completable

    fun getServers(): Observable<Servers>
    fun saveServers(servers: Servers): Completable
}
