package org.tlauncher.tlauncherpe.mc.datalayer.di

import android.app.Application
import android.content.Context
import com.firebase.client.Firebase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application : Application) {

    @Provides
    @Singleton
    internal fun provideApplication() : Application {
        Firebase.setAndroidContext(application.applicationContext)
        return application
    }

    @Provides
    @Singleton
    internal fun providesContext() : Context = application
}