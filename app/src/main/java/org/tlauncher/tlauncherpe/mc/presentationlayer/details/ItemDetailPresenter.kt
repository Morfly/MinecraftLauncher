package org.tlauncher.tlauncherpe.mc.presentationlayer.details

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import android.view.View
import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.tlauncher.tlauncherpe.mc.FileLoader
import org.tlauncher.tlauncherpe.mc.NetworkUtils
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.*
import org.tlauncher.tlauncherpe.mc.domainlayer.details.DetailsInteractor
import org.tlauncher.tlauncherpe.mc.presentationlayer.main.MainActivity
import org.tlauncher.tlauncherpe.mc.unz
import javax.inject.Inject

@PerFragment
class ItemDetailPresenter
@Inject
constructor(val interactor : DetailsInteractor?) : ItemDetailsContract.Presenter() {

    var isPAuse: Boolean = false
    var mime: List<String> = arrayListOf()
    var type: String = ""

    fun isPackageInstalled(packageName : String, packageManager : PackageManager) : Boolean {
        try {
            packageManager.getPackageInfo(packageName, 0)
            return true
        } catch (e : PackageManager.NameNotFoundException) {
            return false
        }
    }

    override fun onDownloadButtonClick(view : View, context: Context) {
        if (isPackageInstalled("com.mojang.minecraftpe", context.packageManager)) {
            when (viewModel.buttonText.get()) {
                R.string.download -> {
                    if (NetworkUtils.isNetworkConnected(context, true)) {
                        val stringRes = when (viewModel.type) {
                            ItemDetailsViewModel.Type.Addon -> R.string.addons_folder
                            ItemDetailsViewModel.Type.Texture -> R.string.textures_folder
                            ItemDetailsViewModel.Type.World -> R.string.maps_folder
                            ItemDetailsViewModel.Type.Skin -> R.string.skins_folder
                            ItemDetailsViewModel.Type.Sid -> R.string.sid_folder
                        }
                        type = view.context.resources.getString(stringRes)
                        mime = viewModel.downloadUrl!!.split("/")
                        val path = "${Environment.getExternalStorageDirectory()}$type${mime[mime.size - 1]}"
                        viewModel.progressVisibility.set(true)
                        viewModel.buttonText.set(R.string.cancel_download)
                        this.view.loadingState(true)
                        subscribe(FileLoader.load(viewModel.id, viewModel.downloadUrl!!, path)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    val progress = ((it.bytesRead * 100) / it.contentLength).toInt()
                                    if (progress == 100) {
                                        val action = if (mime.contains("zip")) {
                                            unz(path, "${Environment.getExternalStorageDirectory()}$type", viewModel.id)
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .flatMapCompletable {
                                                        this.view.loadingState(false)
                                                        return@flatMapCompletable saveToDB(it.path)
                                                    }
                                        } else {
                                            this.view.loadingState(false)
                                            saveToDB(path)
                                        }
                                        subscribe(action
                                                .subscribe({
                                                    this.view.loadingState(false)
                                                    viewModel.buttonText.set(R.string.downloaded)
                                                    viewModel.progressVisibility.set(false)
                                                    this.view.onItemDownloaded()
                                                }, this.view::showError))
                                    }
                                    if (!isPAuse) {
                                        viewModel.progress.set(progress)
                                    }
                                }, {
                                    this.view.showError(it)
                                    this.view.loadingState(false)
                                    onDownloadCancel()
                                }))
                    }
                }
                R.string.cancel_download -> {
                    isPAuse = true
                    this.view.loadingState(false)
                    this.view.onItemDownloadCancel(viewModel.id, type, mime)
                }
            }
        }else{
            this.view.downloadApplication()
        }
        //this.view.openExp()
    }


    override fun saveToDB(path : String?) : Completable {
        return when (viewModel.type) {
            ItemDetailsViewModel.Type.Addon -> {
                val item = MyAddons()
                item.name = viewModel.name
                item.id = viewModel.id
                item.filePath = path
                item.file_size = viewModel.size.toString()
                item.imgs = viewModel.images
                item.date = viewModel.date
                interactor!!.saveAddons(item)
            }
            ItemDetailsViewModel.Type.Texture -> {
                val item = MyTextures()
                item.name = viewModel.name
                item.id = viewModel.id
                item.filePath = path
                item.file_size = viewModel.size.toString()
                item.imgs = viewModel.images
                item.date = viewModel.date
                interactor!!.saveTexture(item)
            }
            ItemDetailsViewModel.Type.World -> {
                val item = MyMods()
                item.name = viewModel.name
                item.id = viewModel.id
                item.filePath = path
                item.file_size = viewModel.size.toString()
                item.imgs = viewModel.images
                item.date = viewModel.date
                interactor!!.saveMods(item)
            }
            ItemDetailsViewModel.Type.Skin -> {
                val item = MySkins()
                item.name = viewModel.name
                item.id = viewModel.id
                item.filePath = path
                item.file_size = viewModel.size.toString()
                item.imgs = viewModel.images
                item.date = viewModel.date
                interactor!!.saveSkins(item)
            }
            ItemDetailsViewModel.Type.Sid -> {
                val item = MySids()
                item.name = viewModel.name
                item.id = viewModel.id
                item.filePath = path
                item.file_size = viewModel.size.toString()
                item.imgs = viewModel.images
                item.date = viewModel.date
                interactor!!.saveSids(item)
            }
        }
    }

    override fun onImageClick() {
        view.showPreview()
    }

    override fun onCloseClick() {
        view.closePhotoPreview()
    }

    override fun onDownloadCancel() {
        isPAuse = false
        FileLoader.cancelLoad(viewModel.id)
        viewModel.progressVisibility.set(false)
        viewModel.progress.set(0)
        viewModel.buttonText.set(R.string.download)
    }
}
