package org.tlauncher.tlauncherpe.mc.presentationlayer.addon.adapter

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
import org.tlauncher.tlauncherpe.mc.FileLoader
import org.tlauncher.tlauncherpe.mc.NetworkUtils
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.databinding.ItemAddonBinding
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MyAddons
import org.tlauncher.tlauncherpe.mc.presentationlayer.addon.AddonContract
import org.tlauncher.tlauncherpe.mc.presentationlayer.addon.AddonItemViewModel
import org.tlauncher.tlauncherpe.mc.presentationlayer.addon.AddonJsonCreator
import org.tlauncher.tlauncherpe.mc.presentationlayer.main.MainActivity
import org.tlauncher.tlauncherpe.mc.unz
import java.io.File
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.HashMap


class AddonAdapter (var data : ArrayList<AddonItemViewModel>, private val listener : View.OnClickListener,
                    var presenter: AddonContract.Presenter, var context: Context, var activity: Activity) : RecyclerView.Adapter<BindingHolder<ItemAddonBinding>>(){
    var file: File? = null
    var isShow: Boolean = false
    var isChacked: Boolean = false
    var id: Int = 0
    val df = DecimalFormat("0.00")
    var oneMB: Long = 1048576
    var showCheckBox: Boolean = false
    var chackMap: HashMap<Int,String> = HashMap()
    val listForJson: ArrayList<AddonItemViewModel> = arrayListOf()

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BindingHolder<ItemAddonBinding>?, position: Int) {
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

        //in my addons list after click button check or check all set checkBox visible
        if (showCheckBox){
            holder?.binding?.checkBox?.visibility = View.VISIBLE
        }else{
            holder?.binding?.checkBox?.visibility = View.GONE
        }

        when (data[position].loaded) {
            //if addon is loaded hide download button, fileSize text and other
            true -> {
                holder?.binding?.download?.visibility = View.GONE
                holder?.binding?.fileSize?.visibility = View.GONE
                //if addon imported to game show power icon(for start game after click) and hide import item
                if (data[position].isImported!!){
                    holder?.binding?.power?.visibility = View.VISIBLE
                    holder?.binding?.add?.visibility = View.GONE
                }else{
                    holder?.binding?.power?.visibility = View.GONE
                    holder?.binding?.add?.visibility = View.VISIBLE
                }
                //isShow is boolean variable for My addons list.
                //If isShow = true, show My addons list views(import from file system button, remove item button)
                //and hide catalog list views(file size, downloads counter, download button)
                if (isShow) {
                    holder?.binding?.buttonCancel?.visibility = View.VISIBLE
                    //holder?.binding?.checkBox?.visibility = View.VISIBLE
                    //if addon imported to game show power icon(for start game after click) and hide import item
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
                    ////
                    //if addon imported to game show power icon(for start game after click) and hide import item
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
                    ////
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
        //after change screen orientation if some addon loading this code will resume loading and show load views
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
                    val f = File(Environment.getExternalStorageDirectory().toString() + context.resources.getString(R.string.addons_folder))
                    if (f.exists()){

                    }else {
                        if (!f.isDirectory) {
                            f.mkdirs()
                        }
                    }
                    // start downloading
                    FileLoader.load(data[position].id, data[position].url!!,
                            Environment.getExternalStorageDirectory().toString() + context.resources.getString(R.string.addons_folder) + mime)
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
                    + context.resources.getString(R.string.addons_folder) + "/" + mime)
            filee.delete()
        }
        //remove file or folder from DB and file system(My addons list)
        holder?.binding?.buttonCancel?.setOnClickListener {
            (activity as MainActivity).removeDirectoty(data[position].filePath!!)
            presenter.removeMyAddonById(data[position].id)
            data.remove(data[position])
            notifyDataSetChanged()
        }

        //start app with some addon in catalog list
        holder?.binding?.power?.setOnClickListener {
            openNotification(data[position])
        }
        //start app with some addon in My addons list
        holder?.binding?.powerTwo?.setOnClickListener {
            openNotification(data[position])
        }
        //you can start app only with 1 addon, this button make this addon main and show power button(catalog list)
        holder?.binding?.add?.setOnClickListener {
            val myAddons: MyAddons = MyAddons()
            myAddons.name = data[position].name
            myAddons.downloads = data[position].downloads
            myAddons.file_size = data[position].fileSize.toString()
            myAddons.id = data[position].id
            myAddons.date = data[position].date
            myAddons.file_url = data[position].url
            myAddons.imgs = data[position].image
            myAddons.text = data[position].text
            myAddons.views = data[position].views
            myAddons.filePath = data[position].filePath
            myAddons.category = data[position].category
            myAddons.type = data[position].type
            myAddons.isImported = true
            //if some addon is main this code remove him from main(isImported = false) and make main clicked addon(isImported = true).
            //if list have not main addon this code just add clicked addon to main
            data.forEachIndexed { index, textureItemViewModel ->
                if (textureItemViewModel.isImported!!) {
                    presenter.updateMyAddonItem(myAddons, position, index)
                }
                if (data.size - 1 == index){
                    if (!textureItemViewModel.isImported!!) {
                        presenter.updateMyAddonItem(myAddons, position, -1)
                    }
                }
            }
        }
//you can start app only with 1 addon, this button make this addon main and show power button(My addon list)
        holder?.binding?.addTwo?.setOnClickListener {
            val myAddons: MyAddons = MyAddons()
            myAddons.name = data[position].name
            myAddons.downloads = data[position].downloads
            myAddons.file_size = data[position].fileSize.toString()
            myAddons.id = data[position].id
            myAddons.date = data[position].date
            myAddons.file_url = data[position].url
            myAddons.imgs = data[position].image
            myAddons.text = data[position].text
            myAddons.views = data[position].views
            myAddons.filePath = data[position].filePath
            myAddons.category = data[position].category
            myAddons.type = data[position].type
            myAddons.isImported = true
            //if some addon is main this code remove him from main(isImported = false) and make main clicked addon(isImported = true).
            //if list have not main addon this code just add clicked addon to main
            data.forEachIndexed { index, textureItemViewModel ->
                if (textureItemViewModel.isImported!!) {
                    presenter.updateMyAddonItem(myAddons,position, index)
                }
                if (data.size - 1 == index){
                    if (!textureItemViewModel.isImported!!) {
                        presenter.updateMyAddonItem(myAddons, position, -1)
                    }
                }
            }
        }

        holder?.binding?.checkBox?.setOnClickListener {
            if (holder.binding.checkBox.isChecked) {
                chackMap.put(data[position].id, data[position].filePath!!)
                listForJson.add(data[position])
            }else{
                chackMap.remove(data[position].id)
                listForJson.remove(data[position])
            }
            presenter.saveCheckedData(chackMap, AddonJsonCreator(listForJson))
        }
    }

    //unziping after load
    fun unzipAfterLoad(mime: String, holder: BindingHolder<ItemAddonBinding>?, datas: AddonItemViewModel, position: Int){
        //if load archive unzip him, if load file do not unzip him
        if (MimeTypeMap.getFileExtensionFromUrl(datas.url as String).equals("zip")) {
            unz(Environment.getExternalStorageDirectory().toString()
                    + context.resources.getString(R.string.addons_folder) + mime,
                    Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.addons_folder), datas.id)

                    .subscribe({
                        activity.runOnUiThread {
                            holder?.binding?.count?.visibility = View.VISIBLE
                            holder?.binding?.downloadCount?.visibility = View.VISIBLE
                            holder?.binding?.progressLay?.visibility = View.GONE
                            holder?.binding?.loadText?.visibility = View.GONE
                            holder?.binding?.unzipText?.visibility = View.GONE
                            //holder?.binding?.power?.visibility = View.VISIBLE
                            holder?.binding?.add?.visibility = View.VISIBLE
                            //increment download counter
                            presenter.downloadCounter(data[position].id)
                            //remove zip file after unziping
                            file = File(Environment.getExternalStorageDirectory().toString()
                                    + context.resources.getString(R.string.addons_folder) + "/" + mime)
                            file?.delete()
                            //save data to MyAddons table in DB
                            val myAddons: MyAddons = MyAddons()
                            myAddons.name = datas.name
                            myAddons.downloads = datas.downloads
                            myAddons.file_size = datas.fileSize.toString()
                            myAddons.id = datas.id
                            myAddons.date = datas.date
                            myAddons.file_url = datas.url
                            myAddons.imgs = datas.image
                            myAddons.text = datas.text
                            myAddons.views = datas.views
                            myAddons.filePath = it.path
                            data[position].filePath = it.path
                            data[position].loaded = true
                            data[position].downloads = (datas.downloads?.toInt()!! + 1).toString()
                            notifyItemChanged(position)
                            myAddons.category = datas.category
                            myAddons.type = datas.type
                            presenter.saveToMy(myAddons, 0)
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
            //holder?.binding?.power?.visibility = View.VISIBLE
            holder?.binding?.add?.visibility = View.VISIBLE
            presenter.downloadCounter(datas.id)
            val myAddons: MyAddons = MyAddons()
            myAddons.name = datas.name
            myAddons.downloads = datas.downloads
            myAddons.file_size = datas.fileSize.toString()
            myAddons.id = datas.id
            myAddons.date = datas.date
            myAddons.file_url = datas.url
            myAddons.imgs = datas.image
            myAddons.text = datas.text
            myAddons.views = datas.views
            myAddons.filePath = Environment.getExternalStorageDirectory().toString() + context.resources.getString(R.string.addons_folder) + mime
            data[position].filePath = Environment.getExternalStorageDirectory().toString() + context.resources.getString(R.string.addons_folder) + mime
            data[position].loaded = true
            data[position].downloads = (datas.downloads?.toInt()!! + 1).toString()
            notifyItemChanged(position)
            myAddons.category = datas.category
            myAddons.type = datas.type
            presenter.saveToMy(myAddons, 0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BindingHolder<ItemAddonBinding> {
        val inflater: LayoutInflater = LayoutInflater.from(parent?.context)
        val binding: ItemAddonBinding = ItemAddonBinding.inflate(inflater, parent, false)
        return BindingHolder(binding)
    }

    fun updateItem(data: ArrayList<AddonItemViewModel>) {
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

    //show My addons list views and hide catalog list views
    fun showMyIcons(isShow: Boolean) {
        this.isShow = isShow
        notifyDataSetChanged()
    }

    //set all checkBoxes in My addons list checked
    fun chackAll(isChacked: Boolean) {
        //this.isChacked = isChacked
        chackMap.entries.forEach {
            presenter.removeMyAddonById(it.key)
            val id = it.key
            val item = data.find { it.id == id }
            data.remove(item)
            (activity as MainActivity).removeDirectoty(it.value)
            //chackMap.remove(item?.id)
        }
        chackMap = HashMap()
        notifyDataSetChanged()
    }

    //set all checkBoxes in My addons list checked
    fun checkAllAndShowCheck(show: Boolean){
        this.isChacked = show
        this.showCheckBox = show
        data.forEachIndexed { index, addonItemViewModel ->
            chackMap.put(addonItemViewModel.id,addonItemViewModel.filePath!!)
        }
        listForJson.addAll(data)
        presenter.saveCheckedData(chackMap, AddonJsonCreator(listForJson))
        notifyDataSetChanged()
    }

    //show checkBoxes in My addons list
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
        var list: ArrayList<AddonItemViewModel> = arrayListOf()
        this.data = list
        notifyDataSetChanged()
    }

    fun openNotification(data: AddonItemViewModel){
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
