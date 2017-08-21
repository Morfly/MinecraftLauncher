package org.tlauncher.tlauncherpe.mc.presentationlayer.addon.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Binds
import dagger.Module
import org.tlauncher.tlauncherpe.mc.domainlayer.addon.AddonInteractor
import org.tlauncher.tlauncherpe.mc.domainlayer.addon.AddonUseCase
import org.tlauncher.tlauncherpe.mc.presentationlayer.addon.AddonContract
import org.tlauncher.tlauncherpe.mc.presentationlayer.addon.AddonPresenter

@Module
interface AddonModule {

    @PerFragment
    @Binds
    fun getAddonInteractor(addonUseCase: AddonUseCase): AddonInteractor

    @Binds
    @PerFragment
    fun addonPresenter(addonPresenter: AddonPresenter) : AddonContract.Presenter
}