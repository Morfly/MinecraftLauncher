package org.tlauncher.tlauncherpe.mc.presentationlayer.skin

import android.content.Context
import com.morfly.cleanarchitecture.core.presentationlayer.BasePresenter
import com.morfly.cleanarchitecture.core.presentationlayer.BaseView
import com.morfly.cleanarchitecture.core.presentationlayer.ViewModel
import org.json.JSONArray
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MySkins
import java.util.*

interface SkinContract {
    interface View: BaseView {
        fun setListData(list: ArrayList<SkinItemViewModel>, refresh: Boolean)
        fun onDownloadButtonClick(url: String/*, pos: Int*/)
        fun updateList(pos: Int, secondPos: Int)
        fun saveCheckedData(checkMap: HashMap<Int, String>, array: JSONArray)
    }

    abstract class Presenter: BasePresenter<SkinContract.View, ViewModel>(){
        abstract fun getListData(context: Context, refresh: Boolean,lang: Int)
        abstract fun startDownload(url:String/*, pos: Int*/)
        abstract fun cancelDownload()
        abstract fun openDetailInfo()
        abstract fun downloadCounter(id: Int)
        abstract fun saveToMy(mySkins: MySkins, loadsState: Int)
        abstract fun getMyList()
        abstract fun removeMySkinById(id:Int)
        abstract fun getMySkinById(id: Int, path: String)
        abstract fun updateMySkinItem(mySkins: MySkins, pos: Int, secondPos: Int)
        abstract fun saveCheckedData(checkMap: HashMap<Int, String>, array: JSONArray)
    }
}