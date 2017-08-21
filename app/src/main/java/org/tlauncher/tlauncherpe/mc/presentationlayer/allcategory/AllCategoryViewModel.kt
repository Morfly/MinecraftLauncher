package org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory

import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.morfly.cleanarchitecture.core.presentationlayer.ViewModel
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Category
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Type
import org.tlauncher.tlauncherpe.mc.presentationlayer.addon.adapter.AddonItem
import org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory.adapter.AllCategoryItem

/**
 * Created by 85064 on 26.07.2017.
 */

class AllCategoryViewModel: BaseObservable(), ViewModel {
    val allcategory : ObservableList<AllCategoryItem> = ObservableArrayList<AllCategoryItem>()
}

class AllCategoryItemViewModel(val type : Int?,
                               val headerName: String?,
                               val data: Data?) : ViewModel