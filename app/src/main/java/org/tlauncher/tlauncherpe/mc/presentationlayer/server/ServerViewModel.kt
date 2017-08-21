package org.tlauncher.tlauncherpe.mc.presentationlayer.server

import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.text.Html
import com.morfly.cleanarchitecture.core.presentationlayer.ViewModel
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Versions
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.adapter.ServerItem

class ServerViewModel : BaseObservable(), ViewModel {
    val server : ObservableList<ServerItem> = ObservableArrayList<ServerItem>()
}

class ServerItemViewModel(var id : Int = 0,
                          var name : String?,
                          var text : String?,
                          var ip : String?,
                          var rank : Int = 0,
                          var versions : Versions? = null) : ViewModel {
    val _text : String
        get() = Html.fromHtml(text).toString()
}


