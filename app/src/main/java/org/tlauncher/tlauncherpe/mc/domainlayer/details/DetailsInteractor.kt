package org.tlauncher.tlauncherpe.mc.domainlayer.details

import android.util.Log
import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import io.reactivex.Completable
import org.tlauncher.tlauncherpe.mc.datalayer.PreferencesManager
import org.tlauncher.tlauncherpe.mc.datalayer.db.DbManager
import org.tlauncher.tlauncherpe.mc.datalayer.db.RealmDbManager
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.*
import javax.inject.Inject

interface DetailsInteractor {
    fun saveAddons(myAddons : MyAddons) : Completable
    fun saveTexture(myTextures : MyTextures) : Completable
    fun saveMods(myMods : MyMods) : Completable
    fun saveSkins(mySkins : MySkins) : Completable
    fun saveSids(mySids : MySids) : Completable
}

@PerFragment
class DetailsUseCase
@Inject
constructor(val manager : PreferencesManager) : DetailsInteractor {
    val dbManager : DbManager = RealmDbManager()
    override fun saveAddons(myAddons : MyAddons) : Completable {
        return dbManager.saveMyAddons(myAddons)
                .doOnComplete { manager.setISLoadAddons(true) }
    }

    override fun saveTexture(myTextures : MyTextures) : Completable {
        return dbManager.saveMyTextures(myTextures)
                .doOnComplete { manager.setISLoadTextures(true) }
    }

    override fun saveMods(myMods : MyMods) : Completable {
        return dbManager.saveMyMods(myMods)
                .doOnComplete { manager.setISLoadMaps(true) }
    }

    override fun saveSkins(mySkins : MySkins) : Completable {
        return dbManager.saveMySkins(mySkins)
                .doOnComplete { manager.setISLoadSkins(true) }
    }

    override fun saveSids(mySids : MySids) : Completable {
        return dbManager.saveMySids(mySids)
                .doOnComplete { manager.setISLoadSids(true) }
    }
}