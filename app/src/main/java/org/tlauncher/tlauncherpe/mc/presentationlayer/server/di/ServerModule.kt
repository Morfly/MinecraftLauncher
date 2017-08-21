package org.tlauncher.tlauncherpe.mc.presentationlayer.server.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Binds
import dagger.Module
import org.tlauncher.tlauncherpe.mc.domainlayer.server.ServerInteractor
import org.tlauncher.tlauncherpe.mc.domainlayer.server.ServerUseCase
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.ServerContract
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.ServerPresenter

@Module
interface ServerModule {

    @PerFragment
    @Binds
    fun serverPresenter(serverPresenter : ServerPresenter) : ServerContract.Presenter

    @PerFragment
    @Binds
    fun serverInteractor(serverUseCase : ServerUseCase) : ServerInteractor

}