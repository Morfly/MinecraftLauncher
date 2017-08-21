package org.tlauncher.tlauncherpe.mc.presentationlayer.skin.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Subcomponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.skin.SkinFragment

@PerFragment
@Subcomponent(modules = arrayOf(SkinModule::class))
interface SkinComponent {
    @Subcomponent.Builder
    interface Builder{
        fun build(): SkinComponent
    }

    fun inject(skinFragment: SkinFragment)
}