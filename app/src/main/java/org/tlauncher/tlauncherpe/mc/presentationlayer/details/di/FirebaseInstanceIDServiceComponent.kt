package org.tlauncher.tlauncherpe.mc.presentationlayer.details.di

import dagger.Subcomponent
import org.tlauncher.tlauncherpe.mc.FirebaseInstanceIDService
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.token.TokenModule


@Subcomponent(modules = arrayOf(TokenModule::class))
interface FirebaseInstanceIDServiceComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): FirebaseInstanceIDServiceComponent
    }

    fun inject(service: FirebaseInstanceIDService)
}