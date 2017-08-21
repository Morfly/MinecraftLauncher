package org.tlauncher.tlauncherpe.mc.presentationlayer.skin.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Binds
import dagger.Module
import org.tlauncher.tlauncherpe.mc.domainlayer.skin.SkinInteractor
import org.tlauncher.tlauncherpe.mc.domainlayer.skin.SkinUseCase
import org.tlauncher.tlauncherpe.mc.presentationlayer.skin.SkinContract
import org.tlauncher.tlauncherpe.mc.presentationlayer.skin.SkinPresenter

@Module
interface SkinModule {

    @PerFragment
    @Binds
    fun getSkinInteractor(skinUseCase: SkinUseCase): SkinInteractor

    @Binds
    @PerFragment
    fun skinPresenter(skinPresenter: SkinPresenter) : SkinContract.Presenter
}