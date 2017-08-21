package org.tlauncher.tlauncherpe.mc.presentationlayer.server

import android.content.Context
import com.astamobi.kristendate.domainlayer.extention.applyApiRequestSchedulers
import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import org.tlauncher.tlauncherpe.mc.NetworkUtils
import org.tlauncher.tlauncherpe.mc.domainlayer.server.ServerInteractor
import javax.inject.Inject

@PerFragment
class ServerPresenter
@Inject
constructor(val serverInteractor : ServerInteractor) : ServerContract.Presenter() {

    override fun getServerList(context : Context, refresh : Boolean) {
        if (NetworkUtils.isNetworkConnected(context, true))
            subscribe(serverInteractor.loadServers()
                    .applyApiRequestSchedulers()
                    .subscribe({ view.setListServers(it, true) }, { view.showError(it) }))
    }

    override fun onFabButtonClick() {
        view?.fabButtonClick()
    }

    init {
        viewModel = ServerViewModel()
    }
}