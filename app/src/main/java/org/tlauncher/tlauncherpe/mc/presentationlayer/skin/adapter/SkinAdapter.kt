package org.tlauncher.tlauncherpe.mc.presentationlayer.skin.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Environment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import com.morfly.cleanarchitecture.core.presentationlayer.adapter.BindingHolder
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import org.tlauncher.tlauncherpe.mc.databinding.ItemSkinBinding
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MySkins
import org.tlauncher.tlauncherpe.mc.presentationlayer.main.MainActivity
import org.tlauncher.tlauncherpe.mc.presentationlayer.skin.SkinContract
import org.tlauncher.tlauncherpe.mc.presentationlayer.skin.SkinItemViewModel
import org.tlauncher.tlauncherpe.mc.presentationlayer.skin.SkinJsonCreator
import java.io.File
import java.text.DecimalFormat
import java.util.*
import com.mcbox.pesdk.archive.util.OptionsUtil
import com.mcbox.pesdk.archive.entity.Options.SKIN_TYPE_Custom
import com.mcbox.pesdk.archive.entity.Options.SKIN_TYPE_Steve
import com.mcbox.pesdk.util.McInstallInfoUtil
import com.mcbox.pesdk.util.LauncherMcVersion.fromVersionString
import com.mcbox.pesdk.launcher.LauncherConstants
import com.mcbox.pesdk.mcfloat.util.LauncherUtil.getPrefs
import android.R.id.edit
import android.app.ActivityManager
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.mcbox.pesdk.archive.entity.Options
import com.mcbox.pesdk.launcher.LauncherConfig
import com.mcbox.pesdk.launcher.LauncherFunc
import com.mcbox.pesdk.launcher.LauncherManager
import com.mcbox.pesdk.mcfloat.util.LauncherUtil
import com.umeng.analytics.MobclickAgent
import com.yy.hiidostatis.api.HiidoSDK
import com.yy.hiidostatis.defs.obj.Property
import com.yy.hiidostatis.inner.BaseStatisContent
import org.tlauncher.tlauncherpe.mc.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.logging.LogManager


class SkinAdapter (var data : ArrayList<SkinItemViewModel>, private val listener : View.OnClickListener,
                   var presenter: SkinContract.Presenter, var context: Context, var activity: Activity) : RecyclerView.Adapter<BindingHolder<ItemSkinBinding>>(){

    var file: File? = null
    var isShow: Boolean = false
    var isChacked: Boolean = false
    var id: Int = 0
    val df = DecimalFormat("0.00")
    var oneMB: Long = 1048576
    var showCheckBox: Boolean = false
    var chackMap: HashMap<Int,String> = HashMap()
    val listForJson: ArrayList<SkinItemViewModel> = arrayListOf()

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BindingHolder<ItemSkinBinding>?, position: Int) {
        holder?.binding?.viewModel = data[position]
        holder?.binding?.root?.tag = position
        holder?.binding?.presenter = presenter
        holder?.binding?.root?.setOnClickListener(listener)
        //set image in image view
        if (data[position].image?.size == 0){
            Picasso.with(context)
                    .load(R.drawable.ic_refresh)
                    .placeholder(R.drawable.ic_refresh)
                    .error(R.drawable.ic_refresh)
                    .into(holder?.binding?.image)
        }else {
            //set default image if image mass from server is empty
            Picasso.with(context)
                    .load(data[position].image?.get(0))
                    .placeholder(R.drawable.ic_refresh)
                    .error(R.drawable.ic_refresh)
                    .into(holder?.binding?.image)
        }
        //if fileSize < 1 megabyte set size in kilobyte else in megabyte
        if (data[position].fileSize!! > oneMB) {
            holder?.binding?.fileSize?.text = df.format(((data[position].fileSize?.toInt()!! / 1024.0) / 1024.0)).toString() + " Мб"
        }else{
            holder?.binding?.fileSize?.text = data[position].fileSize?.toString() + " КБ"
        }
        //in my skins list after click button check or check all set checkBox visible
        if (showCheckBox){
            holder?.binding?.checkBox?.visibility = View.VISIBLE
        }else{
            holder?.binding?.checkBox?.visibility = View.GONE
        }
        when (data[position].loaded) {
        //if skin is loaded hide download button, fileSize text and other
            true -> {
                holder?.binding?.download?.visibility = View.GONE
                holder?.binding?.fileSize?.visibility = View.GONE
                //if skin imported to game show power icon(for start game after click) and hide import item
                if (data[position].isImported!!){
                    holder?.binding?.power?.visibility = View.VISIBLE
                    holder?.binding?.add?.visibility = View.GONE
                }else{
                    holder?.binding?.power?.visibility = View.GONE
                    holder?.binding?.add?.visibility = View.VISIBLE
                }
                //isShow is boolean variable for My skins list.
                //If isShow = true, show My skins list views(import from file system button, remove item button)
                //and hide catalog list views(file size, downloads counter, download button)
                if (isShow) {
                    holder?.binding?.buttonCancel?.visibility = View.VISIBLE
                    //if skin imported to game show power icon(for start game after click) and hide import item
                    if (data[position].isImported!!){
                        holder?.binding?.power?.visibility = View.GONE
                        holder?.binding?.powerTwo?.visibility = View.VISIBLE
                        holder?.binding?.add?.visibility = View.GONE
                        holder?.binding?.addTwo?.visibility = View.GONE
                    }else{
                        holder?.binding?.power?.visibility = View.GONE
                        holder?.binding?.powerTwo?.visibility = View.GONE
                        holder?.binding?.add?.visibility = View.GONE
                        holder?.binding?.addTwo?.visibility = View.VISIBLE
                    }
                    holder?.binding?.download?.visibility = View.GONE
                    holder?.binding?.name?.visibility = View.VISIBLE
                    holder?.binding?.downloadCount?.visibility = View.GONE
                    holder?.binding?.count?.visibility = View.GONE
                } else {
                    holder?.binding?.buttonCancel?.visibility = View.GONE
                    holder?.binding?.checkBox?.visibility = View.GONE
                    //if skin imported to game show power icon(for start game after click) and hide import item
                    if (data[position].isImported!!){
                        holder?.binding?.power?.visibility = View.VISIBLE
                        holder?.binding?.powerTwo?.visibility = View.GONE
                        holder?.binding?.add?.visibility = View.GONE
                        holder?.binding?.addTwo?.visibility = View.GONE
                    }else{
                        holder?.binding?.power?.visibility = View.GONE
                        holder?.binding?.powerTwo?.visibility = View.GONE
                        holder?.binding?.add?.visibility = View.VISIBLE
                        holder?.binding?.addTwo?.visibility = View.GONE
                    }
                    holder?.binding?.name?.visibility = View.VISIBLE
                }
            }
            false -> {
                holder?.binding?.download?.visibility = View.VISIBLE
                holder?.binding?.fileSize?.visibility = View.VISIBLE
                holder?.binding?.power?.visibility = View.GONE
                holder?.binding?.add?.visibility = View.GONE
            }
        }
        //get file mymeType
        val mime = getMimeTypes(data[position].url as String)
        //after change screen orientation if some skin loading this code will resume loading and show load views
        val fff = FileLoader.getLoad(data[position].id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //hide downloads counter and file size and download button,
                    //show progress bar, progress in percentages(%) and progress in MB(KB)
                    holder?.binding?.fileSize?.visibility = View.GONE
                    holder?.binding?.count?.visibility = View.GONE
                    holder?.binding?.downloadCount?.visibility = View.GONE
                    holder?.binding?.progressLay?.visibility = View.VISIBLE
                    holder?.binding?.download?.visibility = View.GONE
                    holder?.binding?.loadText?.visibility = View.VISIBLE
                    val progr = ((it.bytesRead * 100) / it.contentLength).toInt()
                    holder?.binding?.progress?.progress = progr
                    holder?.binding?.textView2?.text = "$progr%"
                    //if fileSize < 1 megabyte set size in kilobyte else in megabyte
                    if (data[position].fileSize?.toLong()!! > oneMB) {
                        holder?.binding?.sizeProgress?.text = " из " + df.format(((data[position].fileSize?.toInt()!! / 1024.0) / 1024.0)).toString() + " Мб"
                        holder?.binding?.sizeProgressTwo?.text = df.format(((((data[position].fileSize?.toLong()!! * progr) / 100) /1024.0)/1024.0)).toString() + " Мб"
                    }else{
                        holder?.binding?.sizeProgress?.text = " из " + data[position].fileSize + " КБ"
                        holder?.binding?.sizeProgressTwo?.text = ((data[position].fileSize?.toInt()!! * progr) / 100).toString() + " КБ"
                    }
                    //if loading finish, start unziping
                    if (progr == 100) {
                        unzipAfterLoad(mime, holder, data[position], position)
                    }
                    Log.w("res","success")
                },{
                    Log.w("res","error")
                })

        holder?.binding?.checkBox?.isChecked = isChacked
        holder?.binding?.download?.setOnClickListener {
            //check internet connecting before loading
            if (NetworkUtils.isNetworkConnected(context, true)) {
                //check do you have game in phone before loading
                if ((activity as MainActivity).isPackageInstalled("com.mojang.minecraftpe", activity.packageManager)) {
                    holder.binding.fileSize.visibility = View.GONE
                    holder.binding.count.visibility = View.GONE
                    holder.binding.downloadCount.visibility = View.GONE
                    holder.binding.progressLay.visibility = View.VISIBLE
                    holder.binding.download.visibility = View.GONE
                    holder.binding.loadText.visibility = View.VISIBLE
                    //create folder with game if need(in Nexus and Lenovo after installing game, game folder is not create)
                    val f = File(Environment.getExternalStorageDirectory().toString() + context.resources.getString(R.string.skins_folder))
                    if (f.exists()){

                    }else {
                        if (!f.isDirectory) {
                            f.mkdirs()
                        }
                    }

                    // start downloading
                    FileLoader.load(data[position].id, data[position].url!!,
                            Environment.getExternalStorageDirectory().toString() + context.resources.getString(R.string.skins_folder) + mime)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                val progr = ((it.bytesRead * 100) / it.contentLength).toInt()
                                holder.binding.progress.progress = progr
                                holder.binding.textView2.text = "$progr%"
                                //if fileSize < 1 megabyte set size in kilobyte else in megabyte
                                if (data[position].fileSize?.toLong()!! > oneMB) {
                                    holder.binding.sizeProgress.text = " из " + df.format(((data[position].fileSize?.toInt()!! / 1024.0) / 1024.0)).toString() + " Мб"
                                    holder.binding.sizeProgressTwo.text = df.format(((((data[position].fileSize?.toLong()!! * progr) / 100) /1024.0)/1024.0)).toString() + " Мб"
                                }else{
                                    holder.binding.sizeProgress.text = " из " + data[position].fileSize + " КБ"
                                    holder.binding.sizeProgressTwo.text = ((data[position].fileSize?.toInt()!! * progr) / 100).toString() + " КБ"
                                }
                                if (progr == 100) {
                                    unzipAfterLoad(mime, holder, data[position], position)
                                }
                                Log.w("Heil", "o/    \\o\\o\\o\\o\\o")
                            }, Throwable::printStackTrace)
                } else {
                    (activity as MainActivity).downloadApplication()
                }
            }
        }

        //stop loading progress and remove file or archive
        holder?.binding?.imageView?.setOnClickListener {
            holder.binding.fileSize.visibility = View.VISIBLE
            holder.binding.count.visibility = View.VISIBLE
            holder.binding.downloadCount.visibility = View.VISIBLE
            holder.binding.progressLay.visibility = View.GONE
            holder.binding.download.visibility = View.VISIBLE
            holder.binding.loadText.visibility = View.GONE
            FileLoader.cancelLoad(data[position].id)
            val filee = File(Environment.getExternalStorageDirectory().toString()
                    + context.resources.getString(R.string.skins_folder) + "/" + mime)
            filee.delete()
        }

        //remove file or folder from DB and file system(My skins list)
        holder?.binding?.buttonCancel?.setOnClickListener {
            (activity as MainActivity).removeDirectoty(data[position].filePath!!)
            presenter.removeMySkinById(data[position].id)
            data.remove(data[position])
            notifyDataSetChanged()
        }

        //start app with some skin in catalog list
        holder?.binding?.power?.setOnClickListener {
            openNotification(data[position])
            //importInGame(data[position].filePath!!, activity)
            ///
            /*val instance = LauncherManager.getInstance()
            if (!(instance == null || instance.launcherFunc == null)) {
                instance.launcherFunc.enableSkin(false)
            }
            val  launcherFunc: LauncherFunc = LauncherManager.getInstance().launcherFunc
            if (launcherFunc != null) {
                val usingSkinPath = launcherFunc.playerSkin
                val isUsingSkin = launcherFunc.isEnabledSkin
            }*/
            ////
            m4878a("start_mcpe", BaseStatisContent.FROM, "我的皮肤列表开启游戏")
            startMC(activity, true, false)
        }

        //start app with some skin in My skins list
        holder?.binding?.powerTwo?.setOnClickListener {
            openNotification(data[position])
            //importInGame(data[position].filePath!!, activity)
            ///
            /*val instance = LauncherManager.getInstance()
            if (!(instance == null || instance.launcherFunc == null)) {
                instance.launcherFunc.enableSkin(false)
            }
            val  launcherFunc: LauncherFunc = LauncherManager.getInstance().launcherFunc
            if (launcherFunc != null) {
                val usingSkinPath = launcherFunc.playerSkin
                val isUsingSkin = launcherFunc.isEnabledSkin
            }*/
            ////
            m4878a("start_mcpe", BaseStatisContent.FROM, "我的皮肤列表开启游戏")
            startMC(activity, true, false)
        }

        //you can start app only with 1 skin, this button make this skin main and show power button(catalog list)
        holder?.binding?.add?.setOnClickListener {
            val mySkins: MySkins = MySkins()
            mySkins.name = data[position].name
            mySkins.downloads = data[position].downloads
            mySkins.file_size = data[position].fileSize.toString()
            mySkins.id = data[position].id
            mySkins.date = data[position].date
            mySkins.file_url = data[position].url
            mySkins.imgs = data[position].image
            mySkins.text = data[position].text
            mySkins.views = data[position].views
            mySkins.filePath = data[position].filePath
            mySkins.category = data[position].category
            mySkins.type = data[position].type
            mySkins.isImported = true
            //if some skin is main this code remove him from main(isImported = false) and make main clicked skin(isImported = true).
            //if list have not main skin this code just add clicked skin to main
            data.forEachIndexed { index, textureItemViewModel ->
                if (textureItemViewModel.isImported!!) {
                    presenter.updateMySkinItem(mySkins, position, index)
                }
                if (data.size - 1 == index){
                    if (!textureItemViewModel.isImported!!) {
                        presenter.updateMySkinItem(mySkins, position, -1)
                    }
                }
            }
            PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(LauncherConstants.PREF_KEY_SKIN_ENABLE, true).apply()
            m4878a("apply_skin", BaseStatisContent.FROM, "我的皮肤列表应用皮肤")
            importInGame(data[position].filePath!!, activity)
        }

        //you can start app only with 1 skin, this button make this skin main and show power button(My skin list)
        holder?.binding?.addTwo?.setOnClickListener {
            val mySkins: MySkins = MySkins()
            mySkins.name = data[position].name
            mySkins.downloads = data[position].downloads
            mySkins.file_size = data[position].fileSize.toString()
            mySkins.id = data[position].id
            mySkins.date = data[position].date
            mySkins.file_url = data[position].url
            mySkins.imgs = data[position].image
            mySkins.text = data[position].text
            mySkins.views = data[position].views
            mySkins.filePath = data[position].filePath
            mySkins.category = data[position].category
            mySkins.type = data[position].type
            mySkins.isImported = true
            //if some skin is main this code remove him from main(isImported = false) and make main clicked skin(isImported = true).
            //if list have not main skin this code just add clicked skin to main
            data.forEachIndexed { index, textureItemViewModel ->
                if (textureItemViewModel.isImported!!) {
                    presenter.updateMySkinItem(mySkins,position, index)
                }
                if (data.size - 1 == index){
                    if (!textureItemViewModel.isImported!!) {
                        presenter.updateMySkinItem(mySkins, position, -1)
                    }
                }
            }
            PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(LauncherConstants.PREF_KEY_SKIN_ENABLE, true).apply()
            m4878a("apply_skin", BaseStatisContent.FROM, "我的皮肤列表应用皮肤")
            importInGame(data[position].filePath!!, activity)
        }

        holder?.binding?.checkBox?.setOnClickListener {
            if (holder.binding.checkBox.isChecked) {
                chackMap.put(data[position].id, data[position].filePath!!)
                listForJson.add(data[position])
            }else{
                chackMap.remove(data[position].id)
                listForJson.remove(data[position])
            }
            presenter.saveCheckedData(chackMap, SkinJsonCreator(listForJson))
        }
    }

    fun m4878a(str: String, vararg strArr: String) {
        m4877a(activity, str, *strArr)
    }

    fun m4877a(context: Context, str: String, vararg strArr: String) {
        if (strArr == null || strArr.size <= 0) {
            MobclickAgent.onEvent(context, str)
            HiidoSDK.instance().reportTimesEvent(0, str, null)
        } else if (strArr.size >= 2 && strArr.size % 2 == 0) {
            val hashMap = HashMap<String,String>()
            var i = 0
            while (i < strArr.size) {
                hashMap.put(strArr[i], strArr[i + 1])
                i += 2
            }
            m4875a(context, str, null, hashMap)
        }
    }

    fun m4875a(context: Context, str: String, str2: String?, map: Map<String, String>) {
        MobclickAgent.onEvent(context, str, map as Map<String, String>)
        val property = Property()
        for (str3 in map.keys) {
            property.putString(str3, map[str3] as String)
        }
        //C0894a.m4874a(context, str, str2, property)
        HiidoSDK.instance().reportTimesEvent(0, str, str2, property)
    }

    //unziping after load
    fun unzipAfterLoad(mime: String, holder: BindingHolder<ItemSkinBinding>?, datas: SkinItemViewModel, position: Int){
        //if load archive unzip him, if load file do not unzip him
        if (MimeTypeMap.getFileExtensionFromUrl(datas.url as String).equals("zip")) {
            unz(Environment.getExternalStorageDirectory().toString()
                    + context.resources.getString(R.string.skins_folder) + mime,
                    Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.skins_folder), datas.id)

                    .subscribe({
                        activity.runOnUiThread {
                            holder?.binding?.count?.visibility = View.VISIBLE
                            holder?.binding?.downloadCount?.visibility = View.VISIBLE
                            holder?.binding?.progressLay?.visibility = View.GONE
                            holder?.binding?.loadText?.visibility = View.GONE
                            holder?.binding?.unzipText?.visibility = View.GONE
                            //holder.binding.power.visibility = View.VISIBLE
                            holder?.binding?.add?.visibility = View.VISIBLE
                            //increment download counter
                            presenter.downloadCounter(datas.id)
                            //remove zip file after unziping
                            file = File(Environment.getExternalStorageDirectory().toString()
                                    + context.resources.getString(R.string.skins_folder) + "/" + mime)
                            file?.delete()
                            //save data to MySkins table in DB
                            val mySkins: MySkins = MySkins()
                            mySkins.name = datas.name
                            mySkins.downloads = datas.downloads
                            mySkins.file_size = datas.fileSize.toString()
                            mySkins.id = datas.id
                            mySkins.date = datas.date
                            mySkins.file_url = datas.url
                            mySkins.imgs = datas.image
                            mySkins.text = datas.text
                            mySkins.views = datas.views
                            mySkins.filePath = it.path
                            data[position].filePath = it.path
                            data[position].loaded = true
                            data[position].downloads = (datas.downloads?.toInt()!! + 1).toString()
                            notifyItemChanged(position)
                            mySkins.category = datas.category
                            mySkins.type = datas.type
                            presenter.saveToMy(mySkins, 0)
                        }
                        Log.e("unzip", "success")
                    }, {
                        Log.e("unzip", "error")
                    })
        }else{
            holder?.binding?.count?.visibility = View.VISIBLE
            holder?.binding?.downloadCount?.visibility = View.VISIBLE
            holder?.binding?.progressLay?.visibility = View.GONE
            holder?.binding?.loadText?.visibility = View.GONE
            holder?.binding?.unzipText?.visibility = View.GONE
            //holder.binding.power.visibility = View.VISIBLE
            holder?.binding?.add?.visibility = View.VISIBLE
            presenter.downloadCounter(datas.id)
            val mySkins: MySkins = MySkins()
            mySkins.name = datas.name
            mySkins.downloads = datas.downloads
            mySkins.file_size = datas.fileSize.toString()
            mySkins.id = datas.id
            mySkins.date = datas.date
            mySkins.file_url = datas.url
            mySkins.imgs = datas.image
            mySkins.text = datas.text
            mySkins.views = datas.views
            mySkins.filePath = Environment.getExternalStorageDirectory().toString() + context.resources.getString(R.string.skins_folder) + mime
            data[position].filePath = Environment.getExternalStorageDirectory().toString() + context.resources.getString(R.string.skins_folder) + mime
            data[position].loaded = true
            data[position].downloads = (datas.downloads?.toInt()!! + 1).toString()
            notifyItemChanged(position)
            mySkins.category = datas.category
            mySkins.type = datas.type
            presenter.saveToMy(mySkins, 0)
        }
    }

    fun startMC(activity: Activity, z: Boolean, z2: Boolean) {
        try {
            //if (checkMcpeInstalled(activity)) {
                m4876a(activity as Context, "start_mc_btn", "",false)
                val fromVersionString = McVersion.fromVersionString(McInstallInfoUtil.getMCVersion(activity))
                if (fromVersionString.major as Int > 0 || fromVersionString.minor as Int >= 12) {
                    val size = getEnabledScripts().size
                    val z3 = getPrefs(0).getBoolean(LauncherConstants.PREF_KEY_SKIN_ENABLE, false)
                    val z4 = getPrefs(0).getBoolean(LauncherConstants.PREF_KEY_TEXTURE_ENABLE, false)
                    val floatingWindowStatue = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("setFloatingWindowStatue", true)
                    val pluginEnable = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("pluginEnable", false)
                    if (fromVersionString.major as Int <= 0 && fromVersionString.minor as Int <= 9) {
                        startMcWithCleanMem(activity, 1)
                        return
                    } else if (fromVersionString.major as Int <= 0 && fromVersionString.minor as Int === 12 && fromVersionString.beta as Int > 0 /*&& fromVersionString.beta as Int < 6*/) {
                        startMcWithCleanMem(activity, 1)
                        return
                    } else if (floatingWindowStatue) {
                        startMcWithCleanMem(activity, 2)
                        return
                    } else if (z3 || z4 || pluginEnable || size != 0) {
                        startMcWithCleanMem(activity, 2)
                        if (z3 && size > 0) {
                            MobclickAgent.onEvent(context, "start_mc_skin_js")
                            HiidoSDK.instance().reportTimesEvent(0, "start_mc_skin_js", null)
                            return
                        } else if (z3) {
                            MobclickAgent.onEvent(context, "start_mc_skin")
                            HiidoSDK.instance().reportTimesEvent(0, "start_mc_skin", null)
                            return
                        } else if (size > 0) {
                            MobclickAgent.onEvent(context, "start_mc_js")
                            HiidoSDK.instance().reportTimesEvent(0, "start_mc_js", null)
                            return
                        } else {
                            return
                        }
                    } else {
                        startMcWithCleanMem(activity, 1)
                        return
                    }
                }
                startMcWithCleanMem(activity, 1)
            //}
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun startMcWithCleanMem(activity: Activity, num: Int?) {
        /*if (if (2 === num) cleanTextureAndPlugin(activity) else true) {
            PrefUtil.setWorldLocation(activity, "")
            if (LauncherUtil.getPrefs(0)!!.getBoolean("isEnableMemClean", true)) {
                val intent = Intent(activity, MemoryCleanActivity::class.java)
                intent.putExtra("startType", num)
                activity.startActivity(intent)
            } else if (num == 1) {*/
        //startPlug(activity)
        activity.startActivity(activity.packageManager.getLaunchIntentForPackage(McInstallInfoUtil.mcPackageName))
            /*} else {
                startPlug(activity)
            }
        }*/
    }

    fun startPlug(activity: Activity) {
        if (isMcRunning(activity)) {
            McInstallInfoUtil.killMc(activity)
        }
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("zz_safe_mode", false).commit()
        startMcWithFloatWindow(activity)
    }

    fun startMcWithFloatWindow(activity: Activity) {
        if (!LauncherManager.getInstance().isPluginOK(activity)) {
            try {
                LauncherManager.getInstance().reloadPlugin(activity)
            } catch (e: Exception) {
                e.printStackTrace()
                m4883c(activity, "startMcWithFloatWindow1 error: MSG=" + e.message + "\nToString=" + e.toString())
            }

        }
        val launcherFunc = LauncherManager.getInstance().launcherFunc
        if (launcherFunc != null) {
            try {
                launcherFunc.startMcWithFloatWindow(activity)
            } catch (e2: Throwable) {
                e2.printStackTrace()
                m4883c(activity, "startMcWithFloatWindow2 error: MSG=" + e2.message + "\nToString=" + e2.toString())
                throw RuntimeException("startMcWithFloatWindow error:", e2)
            }

        }
    }


    fun m4883c(context: Context, str: String) {
        val str2 = "report-error: " + str
        HiidoSDK.instance().reportCrash(0, str2)
        MobclickAgent.reportError(context, str2)
    }

    fun getEnabledScripts(): Set<String> {
        val string = getPrefs(1).getString("enabledScripts", "")
        return if (string == "") HashSet() else HashSet(Arrays.asList(*string!!.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()))
    }

    fun m4876a(context: Context, str: String, str2: String, z: Boolean) {
        //if (!f2943b.booleanValue()) {
            var str3 = ""
            str3 = ""
            if (str.contains("/")) {
                val substring = str.substring(0, str.indexOf("/"))
                str3 = str.substring(str.indexOf("/") + 1)
                val hashMap = HashMap<String,String>()
                hashMap.put("parm1", str3)
                hashMap.put("parm2", str2)
                MobclickAgent.onEvent(context, substring, hashMap)
                val property = Property()
                property.putString("parm1", str3)
                property.putString("parm2", str2)
                HiidoSDK.instance().reportTimesEvent(0, substring, null, property)
                /*if (z && f2942a != null) {
                    f2942a.m1902a(C0575m(str, str).m1843c(str2).mo1835a())
                    return
                }*/
                return
            }
            //C0894a.m4881b(context, str)
            MobclickAgent.onEvent(context, str)
            HiidoSDK.instance().reportTimesEvent(0, str, null)
        //}
    }

    fun importInGame(str: String, activity: Activity){
        var st = str
        if (st == null) {
            try {
                st = ""
            } catch (e: Exception) {
                e.printStackTrace()
                return
            }
        }
        getPrefs(1).edit().putString(LauncherConstants.PREF_KEY_SKIN_PLAYER, st).apply()
        val fromVersionString = McVersion.fromVersionString(McInstallInfoUtil.getMCVersion(activity))
        if (fromVersionString.major as Int >= 0 && fromVersionString.minor as Int >= 0) {
            killMCProgress(activity)
            val c: File
            val options: Options
            if (st == null || st.trim().length <= 0) {
                c = m4930c()
                if (c.exists()) {
                    c.delete()
                }
                options = OptionsUtil.getInstance().options
                options.game_skintype = null
                options.game_lastcustomskin = null
                options.game_skintypefull = Options.SKIN_TYPE_Steve
                options.game_lastcustomskinnew = Options.SKIN_TYPE_Steve
                OptionsUtil.getInstance().writeOptions(options)
                return
            }
            c = File(st)
            if (c.exists()) {
                copyFile(c, m4930c())
                options = OptionsUtil.getInstance().options
                options.game_skintype = null
                options.game_lastcustomskin = null
                options.game_skintypefull = Options.SKIN_TYPE_Custom
                options.game_lastcustomskinnew = Options.SKIN_TYPE_Custom
                OptionsUtil.getInstance().writeOptions(options)
            }
        }
    }

    fun getPrefs(i: Int): SharedPreferences {
        when (i){
            0->{
                return PreferenceManager.getDefaultSharedPreferences(context)
            }
            1->{
                return context.getSharedPreferences("mcpelauncherprefs", 0)
            }
            2->{
                return context.getSharedPreferences("safe_mode_counter", 0)
            }
            else->{
                return null!!
            }
        }
    }

    fun isMcRunning(activity: Activity): Boolean {
        try {
            val activityManager: ActivityManager = activity.getSystemService("activity") as ActivityManager
            if (activityManager != null) {
                val runningAppProcesses: List<ActivityManager.RunningAppProcessInfo> = activityManager.runningAppProcesses
                if (runningAppProcesses != null && runningAppProcesses.size > 0) {
                    runningAppProcesses.forEach {
                        if (McInstallInfoUtil.mcPackageName.equals(it.processName)) {
                            return true
                        }
                    }
                }
            }
            return false
        } catch (e: Exception) {
            return false
        }
    }

    fun killMCProgress(activity: Activity) {
        if (isMcRunning(activity)) {
            McInstallInfoUtil.killMc(activity)
        }
    }

    fun m4930c(): File {
        return File(Environment.getExternalStorageDirectory(), "games/com.mojang/minecraftpe/custom.png")
    }

    fun copyFile(file: File, file2: File) {
        var i = 0
        try {
            if (file.exists()) {
                val fileInputStream = FileInputStream(file)
                val fileOutputStream = FileOutputStream(file2)
                val bArr = ByteArray(1444)
                while (true) {
                    val read = fileInputStream.read(bArr)
                    if (read != -1) {
                        i += read
                        fileOutputStream.write(bArr, 0, read)
                    } else {
                        fileInputStream.close()
                        return
                    }
                }
            }
        } catch (e: Exception) {
            println("Coping file error!")
            e.printStackTrace()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BindingHolder<ItemSkinBinding> {
        val inflater: LayoutInflater = LayoutInflater.from(parent?.context)
        val binding: ItemSkinBinding = ItemSkinBinding.inflate(inflater, parent, false)
        return BindingHolder(binding)
    }

    fun updateItem(data: ArrayList<SkinItemViewModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun sortList(sortType: String) {
        //sorting list by name, date and size
        when (sortType) {
            "name" -> {
                Collections.sort(data) { lhs, rhs -> lhs.name!!.compareTo(rhs.name!!) }
            }
            "fileSize" -> {
                Collections.sort(data) { lhs, rhs -> lhs.fileSize!!.compareTo(rhs.fileSize!!) }
            }
            "date" -> {
                Collections.sort(data) { lhs, rhs -> lhs.date!!.compareTo(rhs.date!!) }
            }
        }
        this.data = data
        notifyDataSetChanged()
    }

    //get file name with mymeType
    fun getMimeTypes(url: String): String {
        val type = url.split("/")
        return type[type.size - 1]
    }

    //show My skins list views and hide catalog list views
    fun showMyIcons(isShow: Boolean) {
        this.isShow = isShow
        notifyDataSetChanged()
    }

    //set all checkBoxes in My skins list checked
    fun chackAll(isChacked: Boolean) {
        //this.isChacked = isChacked
        chackMap.entries.forEach {
            presenter.removeMySkinById(it.key)
            val id = it.key
            val item = data.find { it.id == id }
            data.remove(item)
            (activity as MainActivity).removeDirectoty(it.value)
            //chackMap.remove(item?.id)
        }
        chackMap = HashMap()
        notifyDataSetChanged()
    }

    //set all checkBoxes in My skins list checked
    fun checkAllAndShowCheck(show: Boolean){
        this.isChacked = show
        this.showCheckBox = show
        data.forEachIndexed { index, addonItemViewModel ->
            chackMap.put(addonItemViewModel.id,addonItemViewModel.filePath!!)
        }
        listForJson.addAll(data)
        presenter.saveCheckedData(chackMap, SkinJsonCreator(listForJson))
        notifyDataSetChanged()
    }

    //show checkBoxes in My skins list
    fun showCheckB(show: Boolean){
        this.showCheckBox = show
        notifyDataSetChanged()
    }

    //add new page with data after scroll to end of list
    fun addNewItems(isChacked: Boolean, start: Int, end: Int) {
        this.isChacked = isChacked
        //notifyDataSetChanged()
        notifyItemRangeChanged(start,end)
    }

    //clesar list
    fun clearList() {
        var list: ArrayList<SkinItemViewModel> = arrayListOf()
        this.data = list
        notifyDataSetChanged()
    }

    fun openNotification(data: SkinItemViewModel){
        val customBuilder = AlertDialog.Builder(context)
                .setTitle(context.resources.getString(R.string.how_import_texture))
                .setPositiveButton(android.R.string.ok, { dialog, _ ->
                    run {
                        //(activity as MainActivity).openAddon(data.filePath!!)
                        dialog.dismiss()
                    }
                })
        val dialog = customBuilder.create()
        dialog.show()
        // set green color in dialog button
        val positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        positiveButton?.setTextColor(context.resources.getColor(R.color.progress_color))
    }

    //update 2 items after make some addon main
    fun updateItem(pos: Int, secondPos: Int){
        data[pos].isImported = true
        notifyItemChanged(pos)
        if (secondPos != -1){
            data[secondPos].isImported = false
            notifyItemChanged(secondPos)
        }
    }
}