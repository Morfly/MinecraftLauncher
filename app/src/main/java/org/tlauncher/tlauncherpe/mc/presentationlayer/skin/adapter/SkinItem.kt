package org.tlauncher.tlauncherpe.mc.presentationlayer.skin.adapter

import com.morfly.cleanarchitecture.core.presentationlayer.adapter.BaseBindingItem
import org.tlauncher.tlauncherpe.mc.BR
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.presentationlayer.skin.SkinItemViewModel

class SkinItem (viewModel: SkinItemViewModel): BaseBindingItem<SkinItemViewModel>(viewModel){

    override fun getLayoutId() = R.layout.item_skin

    override fun getViewModelBindingId() = BR.viewModel
}