package org.tlauncher.tlauncherpe.mc.presentationlayer.sid.adapter

import com.morfly.cleanarchitecture.core.presentationlayer.adapter.BaseBindingItem
import org.tlauncher.tlauncherpe.mc.BR
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.presentationlayer.sid.SidItemViewModel

class SidItem (viewModel: SidItemViewModel): BaseBindingItem<SidItemViewModel>(viewModel){

    override fun getLayoutId() = R.layout.item_sid

    override fun getViewModelBindingId() = BR.viewModel
}