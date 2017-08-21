package org.tlauncher.tlauncherpe.mc.presentationlayer.main.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import org.tlauncher.tlauncherpe.mc.ActivityScope
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.servers.ServersRepository
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.servers.ServersRetrofitRepository
import org.tlauncher.tlauncherpe.mc.presentationlayer.main.MainActivity
import java.lang.ref.WeakReference

@Module
class MainModuls(activity: MainActivity) {

    private val weakReference: WeakReference<MainActivity> = WeakReference(activity)

    @Provides
    @ActivityScope
    fun provideContextWeakReference(): WeakReference<MainActivity> = weakReference
}