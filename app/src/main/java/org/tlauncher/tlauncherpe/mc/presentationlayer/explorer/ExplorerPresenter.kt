package org.tlauncher.tlauncherpe.mc.presentationlayer.explorer

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import javax.inject.Inject

@PerFragment
class ExplorerPresenter
@Inject
constructor(): ExplorerContract.Presenter() {

    override fun returnPath(path: String) {
        view.returnPath(path)
    }
}