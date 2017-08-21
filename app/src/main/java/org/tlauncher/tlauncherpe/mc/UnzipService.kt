package org.tlauncher.tlauncherpe.mc

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Environment
import android.os.IBinder
import android.util.Log
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.tlauncher.tlauncherpe.mc.datalayer.db.DbManager
import org.tlauncher.tlauncherpe.mc.datalayer.db.RealmDbManager
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.*
import org.tlauncher.tlauncherpe.mc.presentationlayer.details.ItemDetailsViewModel

/**
 * Created by 85064 on 20.07.2017.
 */
class UnzipService : Service() {

    override fun onBind(intent: Intent?): IBinder {
        return null!!
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        val unzipFilter = IntentFilter()
        unzipFilter.addAction(Constants.UNZIP)
        registerReceiver(unzipReceiver, unzipFilter)
    }

    private val unzipReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == Constants.UNZIP){
                val mime = intent.getStringExtra(Constants.MIME)
                val id = intent.getIntExtra(Constants.RES_ID, 0)
                val path = intent.getStringExtra(Constants.PATH)
                val type = intent.getStringExtra(Constants.TYPE)
                val data = intent.getParcelableExtra<ItemDetailsViewModel>(Constants.DETAIL_ITEM)
                FileLoader.getLoad(id)
                        .subscribe({
                            val progress = ((it.bytesRead * 100) / it.contentLength).toInt()
                            if (progress == 100) {
                                if (mime.equals("zip")) {
                                    unz(path, "${Environment.getExternalStorageDirectory()}$type", id)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .flatMapCompletable {
                                                return@flatMapCompletable saveToDB(path, data, context)
                                            }
                                            .subscribe({Log.w("awdwd","awwdawd")},{Throwable::message})
                                } else {
                                    saveToDB(path, data, context)
                                }
                            }
                        },{Throwable::message})
            }
        }
    }

    fun saveToDB(path : String?, viewModel: ItemDetailsViewModel, context: Context) : Completable {
        val dbManager : DbManager = RealmDbManager()
        return when (viewModel.type) {
            ItemDetailsViewModel.Type.Addon -> {
                val item = MyAddons()
                item.name = viewModel.name
                item.id = viewModel.id
                item.filePath = path
                item.file_size = viewModel.size.toString()
                item.imgs = viewModel.images
                item.date = viewModel.date
                dbManager.saveMyAddons(item)
                        .doOnComplete { setISLoadAddons(true, context) }
                //interactor!!.saveAddons(item)
            }
            ItemDetailsViewModel.Type.Texture -> {
                val item = MyTextures()
                item.name = viewModel.name
                item.id = viewModel.id
                item.filePath = path
                item.file_size = viewModel.size.toString()
                item.imgs = viewModel.images
                item.date = viewModel.date
                dbManager.saveMyTextures(item)
                        .doOnComplete { setISLoadTextures(true, context) }
                //interactor!!.saveTexture(item)
            }
            ItemDetailsViewModel.Type.World -> {
                val item = MyMods()
                item.name = viewModel.name
                item.id = viewModel.id
                item.filePath = path
                item.file_size = viewModel.size.toString()
                item.imgs = viewModel.images
                item.date = viewModel.date
                dbManager.saveMyMods(item)
                        .doOnComplete { setISLoadMaps(true, context) }
                //interactor!!.saveMods(item)
            }
            ItemDetailsViewModel.Type.Skin -> {
                val item = MySkins()
                item.name = viewModel.name
                item.id = viewModel.id
                item.filePath = path
                item.file_size = viewModel.size.toString()
                item.imgs = viewModel.images
                item.date = viewModel.date
                dbManager.saveMySkins(item)
                        .doOnComplete { setISLoadSkins(true, context) }
                //interactor!!.saveSkins(item)
            }
            ItemDetailsViewModel.Type.Sid -> {
                val item = MySids()
                item.name = viewModel.name
                item.id = viewModel.id
                item.filePath = path
                item.file_size = viewModel.size.toString()
                item.imgs = viewModel.images
                item.date = viewModel.date
                dbManager.saveMySids(item)
                        .doOnComplete { setISLoadSids(true, context) }
                //interactor!!.saveSids(item)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}