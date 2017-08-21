package org.tlauncher.tlauncherpe.mc.presentationlayer.addon.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Subcomponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.addon.AddonFragment

@PerFragment
@Subcomponent(modules = arrayOf(AddonModule::class))
interface AddonComponent {
    @Subcomponent.Builder
    interface Builder{
        fun build(): AddonComponent
    }

    fun inject(addonFragment: AddonFragment)
}