package org.tlauncher.tlauncherpe.mc.presentationlayer.explorer.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Subcomponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.explorer.FragmentExplorer

@PerFragment
@Subcomponent(modules = arrayOf(ExplorerModule::class))
interface ExplorerComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build() : ExplorerComponent
    }

    fun inject(fragmentExplorer: FragmentExplorer)
}