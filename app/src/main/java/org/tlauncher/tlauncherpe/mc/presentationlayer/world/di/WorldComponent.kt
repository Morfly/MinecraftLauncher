package org.tlauncher.tlauncherpe.mc.presentationlayer.world.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Subcomponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.world.WorldFragment

@PerFragment
@Subcomponent(modules = arrayOf(WorldModule::class))
interface WorldComponent {
    @Subcomponent.Builder
    interface Builder{
        fun build(): WorldComponent
    }

    fun inject(worldFragment: WorldFragment)
}