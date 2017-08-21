package org.tlauncher.tlauncherpe.mc.presentationlayer.world

import android.content.Context
import com.morfly.cleanarchitecture.core.presentationlayer.BasePresenter
import com.morfly.cleanarchitecture.core.presentationlayer.BaseView
import com.morfly.cleanarchitecture.core.presentationlayer.ViewModel
import org.json.JSONArray
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MyMods
import java.util.*

interface WorldContract {
    interface View: BaseView{
        fun setListData(list: ArrayList<WorldItemViewModel>, refresh: Boolean)
        fun onDownloadButtonClick(url: String/*, pos: Int*/)
        fun saveCheckedData(checkMap: HashMap<Int, String>, array: JSONArray)
    }

    abstract class Presenter: BasePresenter<View,ViewModel>(){
        abstract fun getListData(context: Context, refresh: Boolean,lang: Int)
        abstract fun startDownload(url:String/*, pos: Int*/)
        abstract fun cancelDownload()
        abstract fun openDetailInfo()
        abstract fun downloadCounter(id: Int)
        abstract fun saveToMy(myMods: MyMods,loadsState: Int)
        abstract fun getMyList()
        abstract fun removeMyModById(id:Int)
        abstract fun getMyModById(id: Int, path: String)
        abstract fun saveCheckedData(checkMap: HashMap<Int, String>, array: JSONArray)
    }
}