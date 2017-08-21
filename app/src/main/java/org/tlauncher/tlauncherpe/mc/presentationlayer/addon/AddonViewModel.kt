package org.tlauncher.tlauncherpe.mc.presentationlayer.addon

import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.morfly.cleanarchitecture.core.presentationlayer.ViewModel
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Category
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Type
import org.tlauncher.tlauncherpe.mc.presentationlayer.addon.adapter.AddonItem

class AddonViewModel: BaseObservable(), ViewModel {
    val addon : ObservableList<AddonItem> = ObservableArrayList<AddonItem>()
}

class AddonItemViewModel(val image : List<String>?,
                         val name : String?,
                         var downloads : String?,
                         val fileSize : Long?,
                         val id : Int,
                         val date: String?,
                         val url:String?,
                         var loaded: Boolean,
                         val text: String?,
                         val views: String?,
                         var filePath: String?,
                         val type: Type?,
                         val category: Category?,
                         var isImported: Boolean?) : ViewModel