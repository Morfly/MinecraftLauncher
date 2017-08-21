package org.tlauncher.tlauncherpe.mc.presentationlayer.sid.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Subcomponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.sid.SidFragment

@PerFragment
@Subcomponent(modules = arrayOf(SidModule::class))
interface SidComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): SidComponent
    }

    fun inject(sidFragment: SidFragment)
}