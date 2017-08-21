package org.tlauncher.tlauncherpe.mc.presentationlayer.texture

import android.content.Context
import com.morfly.cleanarchitecture.core.presentationlayer.BasePresenter
import com.morfly.cleanarchitecture.core.presentationlayer.BaseView
import com.morfly.cleanarchitecture.core.presentationlayer.ViewModel
import org.json.JSONArray
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MyTextures
import java.util.*


interface TextureContract {
    interface View: BaseView {
        fun setListData(list: ArrayList<TextureItemViewModel>, refresh: Boolean)
        fun onDownloadButtonClick(url: String/*, pos: Int*/)
        fun updateList(pos: Int, secondPos: Int)
        fun saveCheckedData(checkMap: HashMap<Int, String>, array: JSONArray)
    }

    abstract class Presenter: BasePresenter<View, ViewModel>(){
        abstract fun getListData(context: Context, refresh: Boolean,lang: Int)
        abstract fun startDownload(url:String/*, pos: Int*/)
        abstract fun cancelDownload()
        abstract fun openDetailInfo()
        abstract fun downloadCounter(id: Int)
        abstract fun saveToMy(myTextures: MyTextures, loadsState: Int)
        abstract fun getMyList()
        abstract fun removeMyTextureById(id:Int)
        abstract fun getMyTextureById(id: Int, path: String)
        abstract fun updateMyTextureItem(myTextures: MyTextures, pos: Int, secondPos: Int)
        abstract fun saveCheckedData(checkMap: HashMap<Int, String>, array: JSONArray)
    }
}