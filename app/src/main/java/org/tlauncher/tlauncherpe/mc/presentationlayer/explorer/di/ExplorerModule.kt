package org.tlauncher.tlauncherpe.mc.presentationlayer.explorer.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Binds
import dagger.Module
import org.tlauncher.tlauncherpe.mc.presentationlayer.explorer.ExplorerContract
import org.tlauncher.tlauncherpe.mc.presentationlayer.explorer.ExplorerPresenter


@Module
interface ExplorerModule {

    @PerFragment
    @Binds
    fun explorerPresenter(explorerPresenter: ExplorerPresenter) : ExplorerContract.Presenter
}