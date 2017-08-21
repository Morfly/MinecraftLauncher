package org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory

import com.morfly.cleanarchitecture.core.presentationlayer.BasePresenter
import com.morfly.cleanarchitecture.core.presentationlayer.BaseView
import com.morfly.cleanarchitecture.core.presentationlayer.ViewModel

/**
 * Created by 85064 on 26.07.2017.
 */
interface AllCategoryContract {
    interface View: BaseView {
        fun setList(list: ArrayList<AllCategoryItemViewModel>)
    }

    abstract class Presenter: BasePresenter<View, ViewModel>(){
        abstract fun getList(refresh: Boolean)
    }
}