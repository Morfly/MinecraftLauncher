package org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory.adapter

import com.morfly.cleanarchitecture.BR
import com.morfly.cleanarchitecture.core.presentationlayer.adapter.BaseBindingItem
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory.AllCategoryItemViewModel

/**
 * Created by 85064 on 27.07.2017.
 */
class AllCategoryItem(viewModel: AllCategoryItemViewModel): BaseBindingItem<AllCategoryItemViewModel>(viewModel) {

    override fun getLayoutId() = R.layout.all_category_item

    override fun getViewModelBindingId() = BR.viewModel
}