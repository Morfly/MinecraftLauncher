package org.tlauncher.tlauncherpe.mc.presentationlayer.server

import android.content.Context
import com.morfly.cleanarchitecture.core.presentationlayer.BasePresenter
import com.morfly.cleanarchitecture.core.presentationlayer.BaseView
import com.morfly.cleanarchitecture.core.presentationlayer.ViewModel
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Servers

interface ServerContract {
    interface View : BaseView {
        fun setListServers(list : ArrayList<ServerItemViewModel>, refresh : Boolean)
        fun fabButtonClick()
    }

    abstract class Presenter : BasePresenter<View, ViewModel>() {
        abstract fun getServerList(context : Context, refresh : Boolean)
        abstract fun onFabButtonClick()

    }
}