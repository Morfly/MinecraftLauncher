package org.tlauncher.tlauncherpe.mc.datalayer.db

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.*
import org.tlauncher.tlauncherpe.mc.datalayer.db.entities.*
import org.tlauncher.tlauncherpe.mc.datalayer.db.mapper.toEntity
import org.tlauncher.tlauncherpe.mc.datalayer.db.mapper.toModel
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.*
import java.util.concurrent.TimeUnit

class RealmDbManager : DbManager {

    private val realm: Realm = Realm.getDefaultInstance()

    override fun saveObjects(objects: Objects): Completable = Single.just(objects)
            .map(Objects::toEntity)
            .flatMapCompletable { objEntity -> realm.write { it.insertOrUpdate(objEntity)}}
            .observeOn(Schedulers.io())

    override fun getObjects() =
            realm.read<ObjectsEntity> { it.where(ObjectsEntity::class.java) }
                    .flattenAsObservable { it }
                    .map(ObjectsEntity::toModel)
                    .observeOn(Schedulers.io())

    override fun getMyMods(): Observable<MyMods> =
        realm.read<MyModsEntity> { it.where(MyModsEntity::class.java) }
                .flattenAsObservable { it }
                .map(MyModsEntity::toModel)
                .observeOn(Schedulers.io())


    override fun saveMyMods(myMods: MyMods): Completable = Single.just(myMods)
            .map(MyMods::toEntity)
            .flatMapCompletable { modsEntity -> realm.write { it.copyToRealmOrUpdate(modsEntity)}}
            .observeOn(Schedulers.io())

    override fun getMyModsById(id: Int): Observable<MyMods> =
            realm.read<MyModsEntity> { it.where(MyModsEntity::class.java).equalTo("id",id) }
                    .flattenAsObservable { it }
                    .map(MyModsEntity::toModel)
                    .observeOn(Schedulers.io())

    override fun removeMyModById(id: Int): Completable =
            realm.remove<MyModsEntity> { it.where(MyModsEntity::class.java).equalTo("id",id) }
                    .observeOn(Schedulers.io())

    override fun getSkins() =
            realm.read<ObjectSkinsEntity> { it.where(ObjectSkinsEntity::class.java) }
                    .flattenAsObservable { it }
                    .map(ObjectSkinsEntity::toModel)
                    .observeOn(Schedulers.io())

    override fun saveSkins(skins: Skins): Completable = Single.just(skins)
            .map(Skins::toEntity)
            .flatMapCompletable { objEntity -> realm.write { it.copyToRealmOrUpdate(objEntity)}}
            .observeOn(Schedulers.io())

    override fun getMySkins(): Observable<MySkins> =
            realm.read<MySkinsEntity> { it.where(MySkinsEntity::class.java) }
                    .flattenAsObservable { it }
                    .map(MySkinsEntity::toModel)
                    .observeOn(Schedulers.io())

    override fun saveMySkins(mySkins: MySkins): Completable = Single.just(mySkins)
            .map(MySkins::toEntity)
            .flatMapCompletable { skinsEntity -> realm.write { it.copyToRealmOrUpdate(skinsEntity)}}
            .observeOn(Schedulers.io())

    override fun getMySkinsById(id: Int): Observable<MySkins> =
            realm.read<MySkinsEntity> { it.where(MySkinsEntity::class.java).equalTo("id",id) }
                    .flattenAsObservable { it }
                    .map(MySkinsEntity::toModel)
                    .observeOn(Schedulers.io())

    override fun removeMySkinById(id: Int): Completable =
            realm.remove<MySkinsEntity> { it.where(MySkinsEntity::class.java).equalTo("id",id) }
                    .observeOn(Schedulers.io())

    override fun getMySkinByImport(): Observable<MySkins> =
            realm.read<MySkinsEntity> { it.where(MySkinsEntity::class.java).equalTo("isImported",true) }
                    .flattenAsObservable { it }
                    .map(MySkinsEntity::toModel)
                    .observeOn(Schedulers.io())

    override fun getTextures() =
            realm.read<ObjectsTexturesEntity> { it.where(ObjectsTexturesEntity::class.java) }
                    .flattenAsObservable { it }
                    .map(ObjectsTexturesEntity::toModel)
                    .observeOn(Schedulers.io())

    override fun saveTextures(textures: Textures): Completable = Single.just(textures)
            .map(Textures::toEntity)
            .flatMapCompletable { textureEntity -> realm.write { it.copyToRealmOrUpdate(textureEntity)}}
            .observeOn(Schedulers.io())

    override fun getMyTextures(): Observable<MyTextures> =
            realm.read<MyTexturesEntity> { it.where(MyTexturesEntity::class.java) }
                    .flattenAsObservable { it }
                    .map(MyTexturesEntity::toModel)
                    .observeOn(Schedulers.io())

    override fun saveMyTextures(myTextures: MyTextures): Completable = Single.just(myTextures)
            .map(MyTextures::toEntity)
            .flatMapCompletable { texturesEntity -> realm.write { it.copyToRealmOrUpdate(texturesEntity)}}
            .observeOn(Schedulers.io())

    override fun getMyTextureById(id: Int): Observable<MyTextures> =
            realm.read<MyTexturesEntity> { it.where(MyTexturesEntity::class.java).equalTo("id",id) }
                    .flattenAsObservable { it }
                    .map(MyTexturesEntity::toModel)
                    .observeOn(Schedulers.io())

    override fun getMyTextureByImport(): Observable<MyTextures> =
            realm.read<MyTexturesEntity> { it.where(MyTexturesEntity::class.java).equalTo("isImported",true) }
                    .flattenAsObservable { it }
                    .map(MyTexturesEntity::toModel)
                    .observeOn(Schedulers.io())

    override fun removeMyTextureById(id: Int): Completable =
            realm.remove<MyTexturesEntity> { it.where(MyTexturesEntity::class.java).equalTo("id",id) }
                    .observeOn(Schedulers.io())

    override fun getAddons() =
        realm.read<ObjectAddonsEntity> { it.where(ObjectAddonsEntity::class.java) }
            .flattenAsObservable { it }
            .map(ObjectAddonsEntity::toModel)
            .observeOn(Schedulers.io())

    override fun saveAddons(addons: Addons): Completable = Single.just(addons)
            .map(Addons::toEntity)
            .flatMapCompletable { addonEntity -> realm.write { it.copyToRealmOrUpdate(addonEntity)}}
            .observeOn(Schedulers.io())

    override fun getMyAddons(): Observable<MyAddons> =
            realm.read<MyAddonsEntity> { it.where(MyAddonsEntity::class.java) }
                    .flattenAsObservable { it }
                    .map(MyAddonsEntity::toModel)
                    .observeOn(Schedulers.io())

    override fun saveMyAddons(myAddons: MyAddons): Completable = Single.just(myAddons)
            .map(MyAddons::toEntity)
            .flatMapCompletable { addonsEntity -> realm.write { it.copyToRealmOrUpdate(addonsEntity)}}
            .observeOn(Schedulers.io())

    override fun getMyAddonById(id: Int): Observable<MyAddons> =
            realm.read<MyAddonsEntity> { it.where(MyAddonsEntity::class.java).equalTo("id",id) }
                    .flattenAsObservable { it }
                    .map(MyAddonsEntity::toModel)
                    .observeOn(Schedulers.io())

    override fun removeMyAddonById(id: Int): Completable =
            realm.remove<MyAddonsEntity> { it.where(MyAddonsEntity::class.java).equalTo("id",id) }
                    .observeOn(Schedulers.io())

    override fun getMyAddonByImport(): Observable<MyAddons> =
            realm.read<MyAddonsEntity> { it.where(MyAddonsEntity::class.java).equalTo("isImported",true) }
                    .flattenAsObservable { it }
                    .map(MyAddonsEntity::toModel)
                    .observeOn(Schedulers.io())

    override fun getSids() =
            realm.read<ObjectSidsEntity> { it.where(ObjectSidsEntity::class.java) }
                    .flattenAsObservable { it }
                    .map(ObjectSidsEntity::toModel)
                    .observeOn(Schedulers.io())

    override fun saveSids(sids: Sids): Completable = Single.just(sids)
            .map(Sids::toEntity)
            .flatMapCompletable { sidEntity -> realm.write { it.copyToRealmOrUpdate(sidEntity)}}
            .observeOn(Schedulers.io())

    override fun getMySids(): Observable<MySids> =
            realm.read<MySidsEntity> { it.where(MySidsEntity::class.java) }
                    .flattenAsObservable { it }
                    .map(MySidsEntity::toModel)
                    .observeOn(Schedulers.io())

    override fun saveMySids(mySids: MySids): Completable = Single.just(mySids)
            .map(MySids::toEntity)
            .flatMapCompletable { sidsEntity -> realm.write { it.copyToRealmOrUpdate(sidsEntity)}}
            .observeOn(Schedulers.io())

    override fun getMySidById(id: Int): Observable<MySids> =
            realm.read<MySidsEntity> { it.where(MySidsEntity::class.java).equalTo("id",id) }
                    .flattenAsObservable { it }
                    .map(MySidsEntity::toModel)
                    .observeOn(Schedulers.io())

    override fun removeMySidById(id: Int): Completable =
            realm.remove<MySidsEntity> { it.where(MySidsEntity::class.java).equalTo("id",id) }
                    .observeOn(Schedulers.io())

    override fun getServers() =
            realm.read<ServersEntity> { it.where(ServersEntity::class.java) }
                    .flattenAsObservable { it }
                    .map(ServersEntity::toModel)
                    .observeOn(Schedulers.io())

    override fun saveServers(servers: Servers): Completable = Single.just(servers)
            .map(Servers::toEntity)
            .flatMapCompletable { serverEntity -> realm.write { it.copyToRealmOrUpdate(serverEntity)}}
            .observeOn(Schedulers.io())

    override fun close() = realm.close()
}



inline fun <reified T : RealmObject> Realm.read(crossinline action: (Realm) -> RealmQuery<T>): Single<List<T>> =
        Single.create<List<T>> { emitter ->
            val results = action.invoke(this).findAllAsync()
            results.addChangeListener(object : RealmChangeListener<RealmResults<T>> {
                override fun onChange(element: RealmResults<T>) {
                    element.removeChangeListener(this)
                    emitter.onSuccess(element)
                }
            })

        }.subscribeOn(AndroidSchedulers.mainThread())
                .timeout(500, TimeUnit.MILLISECONDS)
                .retry(5)
                .observeOn(AndroidSchedulers.mainThread())


inline fun Realm.write(crossinline transaction: (Realm) -> Unit): Completable =
        Completable.create { emitter ->
            val asyncTask = this.executeTransactionAsync(
                    {transaction(it)},
                    {emitter.onComplete()},
                    {emitter.onError(it)})
            emitter.setCancellable { asyncTask.cancel() }
        }.subscribeOn(AndroidSchedulers.mainThread())

inline fun <reified T : RealmObject> Realm.remove(crossinline action: (Realm) -> RealmQuery<T>): Completable =
        Completable.create { emitter ->
            beginTransaction()
            val isSuccess = action.invoke(this).findAll().deleteAllFromRealm()
            commitTransaction()
            if(isSuccess) emitter.onComplete()
            else emitter.onError(IllegalStateException("Items are not deleted"))
        }.subscribeOn(AndroidSchedulers.mainThread())