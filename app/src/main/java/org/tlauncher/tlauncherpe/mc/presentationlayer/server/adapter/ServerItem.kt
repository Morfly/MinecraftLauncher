package org.tlauncher.tlauncherpe.mc.presentationlayer.server.adapter

import com.morfly.cleanarchitecture.BR
import com.morfly.cleanarchitecture.core.presentationlayer.adapter.BaseBindingItem
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.ServerItemViewModel

class ServerItem(viewModel : ServerItemViewModel) : BaseBindingItem<ServerItemViewModel>(viewModel) {

    override fun getLayoutId() : Int = R.layout.item_servers

    override fun getViewModelBindingId() : Int = BR.viewModel
}