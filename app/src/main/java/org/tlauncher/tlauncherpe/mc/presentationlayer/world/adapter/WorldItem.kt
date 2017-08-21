package org.tlauncher.tlauncherpe.mc.presentationlayer.world.adapter

import com.morfly.cleanarchitecture.BR
import com.morfly.cleanarchitecture.core.presentationlayer.adapter.BaseBindingItem
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.presentationlayer.world.WorldItemViewModel

class WorldItem (viewModel: WorldItemViewModel): BaseBindingItem<WorldItemViewModel>(viewModel){

    override fun getLayoutId() = R.layout.item_world

    override fun getViewModelBindingId() = BR.viewModel
}