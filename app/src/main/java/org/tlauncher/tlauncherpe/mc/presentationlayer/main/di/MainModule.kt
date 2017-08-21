package org.tlauncher.tlauncherpe.mc.presentationlayer.main.di

import dagger.Binds
import dagger.Module
import org.tlauncher.tlauncherpe.mc.ActivityScope
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.servers.ServersRepository
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.servers.ServersRetrofitRepository
import org.tlauncher.tlauncherpe.mc.domainlayer.main.MainInteractor
import org.tlauncher.tlauncherpe.mc.domainlayer.main.MainUseCase
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.ServerContract
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.ServerPresenter
import org.tlauncher.tlauncherpe.mc.presentationlayer.world.WorldContract
import org.tlauncher.tlauncherpe.mc.presentationlayer.world.WorldPresenter

@Module
interface MainModule {

    @Binds
    @ActivityScope
    fun getMainInteractor(mainUseCase : MainUseCase) : MainInteractor

    @Binds
    @ActivityScope
    fun worldPresenter(worldPresenter : WorldPresenter) : WorldContract.Presenter

    @Binds
    @ActivityScope
    fun serverPresenter(serverPresenter : ServerPresenter):ServerContract.Presenter
}