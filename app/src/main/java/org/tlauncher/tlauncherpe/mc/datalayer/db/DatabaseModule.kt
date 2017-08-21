package org.tlauncher.tlauncherpe.mc.datalayer.db

import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import org.tlauncher.tlauncherpe.mc.ActivityScope

@Module
class DatabaseModule {
    @Provides
    @ActivityScope
    fun provideDatabaseManager(): DbManager {
        val configuration = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(configuration)
        return RealmDbManager()
    }
}