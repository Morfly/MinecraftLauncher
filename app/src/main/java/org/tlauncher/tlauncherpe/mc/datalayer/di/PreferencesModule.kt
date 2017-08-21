package org.tlauncher.tlauncherpe.mc.datalayer.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import org.tlauncher.tlauncherpe.mc.datalayer.PreferencesManager
import org.tlauncher.tlauncherpe.mc.datalayer.SharedPreferencesManager
import javax.inject.Singleton

@Module
class PreferencesModule {

    @Provides
    @Singleton
    fun providePreferencesManager(application: Application): PreferencesManager {
        val preferences = application.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val gson = Gson()
        return SharedPreferencesManager(preferences)
    }

}