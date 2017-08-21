package org.tlauncher.tlauncherpe.mc.presentationlayer.explorer

import com.morfly.cleanarchitecture.core.presentationlayer.BasePresenter
import com.morfly.cleanarchitecture.core.presentationlayer.BaseView
import com.morfly.cleanarchitecture.core.presentationlayer.ViewModel

interface ExplorerContract {
    interface View: BaseView {
        fun returnPath(path: String)
    }

    abstract class Presenter: BasePresenter<View, ViewModel>(){
        abstract fun returnPath(path: String)
    }
}