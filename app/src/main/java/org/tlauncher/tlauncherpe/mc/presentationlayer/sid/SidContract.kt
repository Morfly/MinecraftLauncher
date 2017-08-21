package org.tlauncher.tlauncherpe.mc.presentationlayer.sid

import android.content.Context
import com.morfly.cleanarchitecture.core.presentationlayer.BasePresenter
import com.morfly.cleanarchitecture.core.presentationlayer.BaseView
import com.morfly.cleanarchitecture.core.presentationlayer.ViewModel
import org.json.JSONArray
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MySids
import java.util.*

interface SidContract {
    interface View: BaseView {
        fun setListData(list: ArrayList<SidItemViewModel>, refresh: Boolean)
        fun onDownloadButtonClick(url: String/*, pos: Int*/)
        fun saveCheckedData(checkMap: HashMap<Int, String>, array: JSONArray)
    }

    abstract class Presenter: BasePresenter<SidContract.View, ViewModel>(){
        abstract fun getListData(context: Context, refresh: Boolean,lang: Int)
        abstract fun startDownload(url:String/*, pos: Int*/)
        abstract fun cancelDownload()
        abstract fun openDetailInfo()
        abstract fun downloadCounter(id: Int)
        abstract fun saveToMy(mySids: MySids, loadsState: Int)
        abstract fun getMyList()
        abstract fun removeMySidById(id:Int)
        abstract fun getMySidById(id: Int, path: String)
        abstract fun saveCheckedData(checkMap: HashMap<Int, String>, array: JSONArray)
    }
}