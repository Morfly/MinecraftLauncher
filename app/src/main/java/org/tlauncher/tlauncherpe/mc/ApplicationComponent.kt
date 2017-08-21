package org.tlauncher.tlauncherpe.mc

import dagger.Component
import org.tlauncher.tlauncherpe.mc.datalayer.di.ApplicationModule
import org.tlauncher.tlauncherpe.mc.datalayer.di.PreferencesModule
import org.tlauncher.tlauncherpe.mc.datalayer.di.RetrofitApiModule
import org.tlauncher.tlauncherpe.mc.presentationlayer.details.di.DetailsComponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.details.di.FirebaseInstanceIDServiceComponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.main.di.ActivityComponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.di.ServerComponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.di.ServerModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        PreferencesModule::class,
        RetrofitApiModule::class))
interface ApplicationComponent {

    fun plusActivityComponent(): ActivityComponent.Builder

    fun plusDetailsComponent(): DetailsComponent.Builder

    fun plusFirebaseInstanceIDService(): FirebaseInstanceIDServiceComponent.Builder

//    fun plusServerComponent() : ServerComponent.Builder

    //fun plusMainComponent(): MainComponent.Builder

    //fun plusWorldComponent(): WorldComponent.Builder
}