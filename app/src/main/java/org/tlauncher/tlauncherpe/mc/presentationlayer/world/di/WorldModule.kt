package org.tlauncher.tlauncherpe.mc.presentationlayer.world.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Binds
import dagger.Module
import org.tlauncher.tlauncherpe.mc.domainlayer.world.WorldInteractor
import org.tlauncher.tlauncherpe.mc.domainlayer.world.WorldUseCase
import org.tlauncher.tlauncherpe.mc.presentationlayer.world.WorldContract
import org.tlauncher.tlauncherpe.mc.presentationlayer.world.WorldPresenter

@Module
interface WorldModule {

    @PerFragment
    @Binds
    fun getWorldInteractor(worldUseCase: WorldUseCase): WorldInteractor

    @Binds
    @PerFragment
    fun worldPresenter(worldPresenter: WorldPresenter) : WorldContract.Presenter
}