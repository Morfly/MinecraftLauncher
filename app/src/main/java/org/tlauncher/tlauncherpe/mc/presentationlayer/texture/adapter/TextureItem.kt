package org.tlauncher.tlauncherpe.mc.presentationlayer.texture.adapter

import com.morfly.cleanarchitecture.BR
import com.morfly.cleanarchitecture.core.presentationlayer.adapter.BaseBindingItem
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.presentationlayer.texture.TextureItemViewModel

class TextureItem (viewModel: TextureItemViewModel): BaseBindingItem<TextureItemViewModel>(viewModel){

    override fun getLayoutId() = R.layout.item_texture

    override fun getViewModelBindingId() = BR.viewModel
}