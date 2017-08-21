package org.tlauncher.tlauncherpe.mc.presentationlayer.addon.adapter

import com.morfly.cleanarchitecture.BR
import com.morfly.cleanarchitecture.core.presentationlayer.adapter.BaseBindingItem
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.presentationlayer.addon.AddonItemViewModel

class AddonItem (viewModel: AddonItemViewModel): BaseBindingItem<AddonItemViewModel>(viewModel){

    override fun getLayoutId() = R.layout.item_addon

    override fun getViewModelBindingId() = BR.viewModel
}