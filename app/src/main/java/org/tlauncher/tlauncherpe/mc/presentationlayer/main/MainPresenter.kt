package org.tlauncher.tlauncherpe.mc.presentationlayer.main

import com.morfly.cleanarchitecture.core.di.scope.PerActivity
import javax.inject.Inject

@PerActivity
class MainPresenter
@Inject
constructor(/*mainInteractor : MainInteractor*/) : MainContract.Presenter() {

    init {
        viewModel = MainViewModel()
    }

    override fun onFabButtonClick() {
        view?.fabButtonClick()
    }
}