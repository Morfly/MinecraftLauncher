package org.tlauncher.tlauncherpe.mc.presentationlayer.sid.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Binds
import dagger.Module
import org.tlauncher.tlauncherpe.mc.domainlayer.sid.SidInteractor
import org.tlauncher.tlauncherpe.mc.domainlayer.sid.SidUseCase
import org.tlauncher.tlauncherpe.mc.presentationlayer.sid.SidContract
import org.tlauncher.tlauncherpe.mc.presentationlayer.sid.SidPresenter

@Module
interface SidModule {

    @PerFragment
    @Binds
    fun getSidInteractor(sidUseCase: SidUseCase): SidInteractor

    @Binds
    @PerFragment
    fun sidPresenter(sidPresenter: SidPresenter) : SidContract.Presenter
}