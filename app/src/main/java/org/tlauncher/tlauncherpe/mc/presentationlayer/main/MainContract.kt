package org.tlauncher.tlauncherpe.mc.presentationlayer.main

import com.morfly.cleanarchitecture.core.presentationlayer.BasePresenter
import com.morfly.cleanarchitecture.core.presentationlayer.BaseView
import com.morfly.cleanarchitecture.core.presentationlayer.ViewModel

interface MainContract {

    interface View : BaseView {
        fun fabButtonClick()
    }

    abstract class Presenter : BasePresenter<View, MainViewModel>() {
        abstract fun onFabButtonClick()
    }
}