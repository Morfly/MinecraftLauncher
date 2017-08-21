package org.tlauncher.tlauncherpe.mc.presentationlayer.server.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Subcomponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.ServerFragment

@PerFragment
@Subcomponent(modules = arrayOf(ServerModule::class))
interface ServerComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build() : ServerComponent
    }

    fun inject(serverFragment : ServerFragment)
}