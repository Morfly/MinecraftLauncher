package org.tlauncher.tlauncherpe.mc.presentationlayer.explorer

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.morfly.cleanarchitecture.core.presentationlayer.ViewModel
import org.tlauncher.tlauncherpe.mc.BR

class ItemExplorerViewModel : ViewModel, BaseObservable() {

    var name: String = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

}
