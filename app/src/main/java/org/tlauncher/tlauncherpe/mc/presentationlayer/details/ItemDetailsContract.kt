package org.tlauncher.tlauncherpe.mc.presentationlayer.details

import android.app.Activity
import android.content.Context
import com.morfly.cleanarchitecture.core.presentationlayer.BasePresenter
import com.morfly.cleanarchitecture.core.presentationlayer.BaseView
import io.reactivex.Completable

interface ItemDetailsContract {
    interface View : BaseView {
        fun onItemDownloadCancel(id: Int, type: String, mime: List<String>)
        fun showPreview()
        fun closePhotoPreview()
        fun onItemDownloaded()
        fun openExp()
        fun downloadApplication()
        fun loadingState(loading: Boolean)
    }

    abstract class Presenter : BasePresenter<View, ItemDetailsViewModel>() {
        abstract fun onDownloadButtonClick(view : android.view.View, context: Context)
        abstract fun onImageClick()
        abstract fun onCloseClick()
        abstract fun onDownloadCancel()
        abstract fun saveToDB(path : String?) : Completable
    }
}