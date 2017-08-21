package org.tlauncher.tlauncherpe.mc.presentationlayer.details.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Binds
import dagger.Module
import org.tlauncher.tlauncherpe.mc.domainlayer.details.DetailsInteractor
import org.tlauncher.tlauncherpe.mc.domainlayer.details.DetailsUseCase
import org.tlauncher.tlauncherpe.mc.presentationlayer.details.ItemDetailPresenter
import org.tlauncher.tlauncherpe.mc.presentationlayer.details.ItemDetailsContract

@Module
interface DetailsModule {
    @PerFragment
    @Binds
    fun provideDetailsInteractor(useCase : DetailsUseCase) : DetailsInteractor

    @PerFragment
    @Binds
    fun provideDetailsPresenter(presenter : ItemDetailPresenter) : ItemDetailsContract.Presenter
}