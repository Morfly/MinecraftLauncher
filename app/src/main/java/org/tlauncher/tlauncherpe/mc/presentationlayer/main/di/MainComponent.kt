package org.tlauncher.tlauncherpe.mc.presentationlayer.main.di

import com.morfly.cleanarchitecture.core.di.scope.PerActivity
import dagger.Subcomponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.main.MainActivity

@PerActivity
@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {
    @Subcomponent.Builder
    interface Builder{
        fun build(): MainComponent
    }

    fun inject(mainActivity: MainActivity)
}