package org.tlauncher.tlauncherpe.mc.presentationlayer.addon

import android.content.Context
import com.morfly.cleanarchitecture.core.presentationlayer.BasePresenter
import com.morfly.cleanarchitecture.core.presentationlayer.BaseView
import com.morfly.cleanarchitecture.core.presentationlayer.ViewModel
import org.json.JSONArray
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MyAddons
import java.util.*

interface AddonContract {
    interface View: BaseView {
        fun setListData(list: ArrayList<AddonItemViewModel>, refresh: Boolean)
        fun onDownloadButtonClick(url: String/*, pos: Int*/)
        fun updateList(pos: Int, secondPos: Int)
        fun saveCheckedData(checkMap: HashMap<Int, String>, array: JSONArray)
    }

    abstract class Presenter: BasePresenter<AddonContract.View, ViewModel>(){
        abstract fun getListData(context: Context, refresh: Boolean,lang: Int)
        abstract fun startDownload(url:String/*, pos: Int*/)
        abstract fun cancelDownload()
        abstract fun openDetailInfo()
        abstract fun downloadCounter(id: Int)
        abstract fun saveToMy(myAddons: MyAddons, loadsState: Int)
        abstract fun getMyList()
        abstract fun removeMyAddonById(id:Int)
        abstract fun getMyAddonById(id: Int, path: String)
        abstract fun updateMyAddonItem(myAddons: MyAddons, pos: Int, secondPos: Int)
        abstract fun saveCheckedData(checkMap: HashMap<Int, String>, array: JSONArray)
    }
}