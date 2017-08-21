package org.tlauncher.tlauncherpe.mc.presentationlayer.sid.adapter

import android.app.Activity
import android.content.Context
import android.os.Environment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.morfly.cleanarchitecture.core.presentationlayer.adapter.BindingHolder
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import org.tlauncher.tlauncherpe.mc.FileLoader
import org.tlauncher.tlauncherpe.mc.NetworkUtils
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.databinding.ItemSidBinding
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MySids
import org.tlauncher.tlauncherpe.mc.presentationlayer.main.MainActivity
import org.tlauncher.tlauncherpe.mc.presentationlayer.sid.SidContract
import org.tlauncher.tlauncherpe.mc.presentationlayer.sid.SidItemViewModel
import org.tlauncher.tlauncherpe.mc.presentationlayer.sid.SidJsonCreator
import org.tlauncher.tlauncherpe.mc.unz
import java.io.File
import java.text.DecimalFormat
import java.util.*

class SidAdapter (var data : ArrayList<SidItemViewModel>, private val listener : View.OnClickListener,
                  var presenter: SidContract.Presenter, var context: Context, var activity: Activity) : RecyclerView.Adapter<BindingHolder<ItemSidBinding>>(){

    var file: File? = null
    var isShow: Boolean = false
    var isChacked: Boolean = false
    var id: Int = 0
    val df = DecimalFormat("0.00")
    var oneMB: Long = 1048576
    var showCheckBox: Boolean = false
    var chackMap: HashMap<Int,String> = HashMap()
    val listForJson: ArrayList<SidItemViewModel> = arrayListOf()

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BindingHolder<ItemSidBinding>?, position: Int) {
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
        //in my sids list after click button check or check all set checkBox visible
        if (showCheckBox){
            holder?.binding?.checkBox?.visibility = View.VISIBLE
        }else{
            holder?.binding?.checkBox?.visibility = View.GONE
        }
        when (data[position].loaded) {
        //if sid is loaded hide download button, fileSize text and other
            true -> {
                holder?.binding?.download?.visibility = View.GONE
                holder?.binding?.fileSize?.visibility = View.GONE
                //holder?.binding?.power?.visibility = View.VISIBLE
                holder?.binding?.add?.visibility = View.VISIBLE
                //isShow is boolean variable for My sids list.
                //If isShow = true, show My sids list views(import from file system button, remove item button)
                //and hide catalog list views(file size, downloads counter, download button)
                if (isShow) {
                    holder?.binding?.buttonCancel?.visibility = View.VISIBLE
                    //holder?.binding?.checkBox?.visibility = View.VISIBLE
                    /*holder?.binding?.power?.visibility = View.GONE
                    holder?.binding?.powerTwo?.visibility = View.VISIBLE*/
                    holder?.binding?.add?.visibility = View.GONE
                    holder?.binding?.addTwo?.visibility = View.VISIBLE
                    holder?.binding?.download?.visibility = View.GONE
                    holder?.binding?.name?.visibility = View.VISIBLE
                    holder?.binding?.downloadCount?.visibility = View.GONE
                    holder?.binding?.count?.visibility = View.GONE
                } else {
                    holder?.binding?.buttonCancel?.visibility = View.GONE
                    holder?.binding?.checkBox?.visibility = View.GONE
                    /*holder?.binding?.power?.visibility = View.VISIBLE
                    holder?.binding?.powerTwo?.visibility = View.GONE*/
                    holder?.binding?.add?.visibility = View.VISIBLE
                    holder?.binding?.addTwo?.visibility = View.GONE
                    holder?.binding?.name?.visibility = View.VISIBLE
                }
            }
            false -> {
                holder?.binding?.download?.visibility = View.VISIBLE
                holder?.binding?.fileSize?.visibility = View.VISIBLE
                //holder?.binding?.power?.visibility = View.GONE
                holder?.binding?.add?.visibility = View.GONE
            }
        }
        //get file mymeType
        val mime = getMimeTypes(data[position].url as String)
        //after change screen orientation if some sid loading this code will resume loading and show load views
        /*val fff = FileLoader.getLoad(data[position].id)
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
                })*/

        holder?.binding?.checkBox?.isChecked = isChacked
        holder?.binding?.download?.setOnClickListener {
            //check internet connecting before loading
            Toast.makeText(context,"Add sid in game",Toast.LENGTH_SHORT).show()
            /*if (NetworkUtils.isNetworkConnected(context, true)) {
                //check do you have game in phone before loading
                if ((activity as MainActivity).isPackageInstalled("com.mojang.minecraftpe", activity.packageManager)) {
                    holder.binding.fileSize.visibility = View.GONE
                    holder.binding.count.visibility = View.GONE
                    holder.binding.downloadCount.visibility = View.GONE
                    holder.binding.progressLay.visibility = View.VISIBLE
                    holder.binding.download.visibility = View.GONE
                    holder.binding.loadText.visibility = View.VISIBLE

                    //create folder with game if need(in Nexus and Lenovo after installing game, game folder is not create)
                    val f = File(Environment.getExternalStorageDirectory().toString() + context.resources.getString(R.string.sid_folder))
                    if (f.exists()){

                    }else {
                        if (!f.isDirectory) {
                            f.mkdirs()
                        }
                    }

                    // start downloading
                    FileLoader.load(data[position].id, data[position].url!!,
                            Environment.getExternalStorageDirectory().toString() + context.resources.getString(R.string.sid_folder) + mime)
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
            }*/
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
                    + context.resources.getString(R.string.sid_folder) + "/" + mime)
            filee.delete()
        }

        //remove file or folder from DB and file system(My sids list)
        holder?.binding?.buttonCancel?.setOnClickListener {
            (activity as MainActivity).removeDirectoty(data[position].filePath!!)
            presenter.removeMySidById(data[position].id)
            data.remove(data[position])
            notifyDataSetChanged()
        }

        /*holder?.binding?.power?.setOnClickListener {
            (activity as MainActivity).openAddon(data[position].filePath!!)
        }

        holder?.binding?.powerTwo?.setOnClickListener {
            (activity as MainActivity).openAddon(data[position].filePath!!)
        }*/

        holder?.binding?.checkBox?.setOnClickListener {
            if (holder.binding.checkBox.isChecked) {
                chackMap.put(data[position].id, data[position].filePath!!)
                listForJson.add(data[position])
            }else{
                chackMap.remove(data[position].id)
                listForJson.remove(data[position])
            }
            presenter.saveCheckedData(chackMap, SidJsonCreator(listForJson))
        }
    }

    //unziping after load
    fun unzipAfterLoad(mime: String, holder: BindingHolder<ItemSidBinding>?, datas: SidItemViewModel, position: Int){
        //if load archive unzip him, if load file do not unzip him
        if (MimeTypeMap.getFileExtensionFromUrl(datas.url as String).equals("zip")) {
            unz(Environment.getExternalStorageDirectory().toString()
                    + context.resources.getString(R.string.sid_folder) + mime,
                    Environment.getExternalStorageDirectory().toString()
                            + context.resources.getString(R.string.sid_folder), datas.id)

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
                                    + context.resources.getString(R.string.sid_folder) + "/" + mime)
                            file?.delete()
                            //save data to MySids table in DB
                            val mySids: MySids = MySids()
                            mySids.name = datas.name
                            mySids.downloads = datas.downloads
                            mySids.file_size = datas.fileSize.toString()
                            mySids.id = datas.id
                            mySids.date = datas.date
                            mySids.file_url = datas.url
                            mySids.imgs = datas.image
                            mySids.text = datas.text
                            mySids.views = datas.views
                            mySids.filePath = it.path
                            data[position].filePath = it.path
                            data[position].loaded = true
                            data[position].downloads = (datas.downloads?.toInt()!! + 1).toString()
                            notifyItemChanged(position)
                            mySids.category = datas.category
                            mySids.type = datas.type
                            presenter.saveToMy(mySids, 0)
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
            val mySids: MySids = MySids()
            mySids.name = datas.name
            mySids.downloads = datas.downloads
            mySids.file_size = datas.fileSize.toString()
            mySids.id = datas.id
            mySids.date = datas.date
            mySids.file_url = datas.url
            mySids.imgs = datas.image
            mySids.text = datas.text
            mySids.views = datas.views
            mySids.filePath = Environment.getExternalStorageDirectory().toString() + context.resources.getString(R.string.sid_folder) + mime
            data[position].filePath = Environment.getExternalStorageDirectory().toString() + context.resources.getString(R.string.sid_folder) + mime
            data[position].loaded = true
            data[position].downloads = (datas.downloads?.toInt()!! + 1).toString()
            notifyItemChanged(position)
            mySids.category = datas.category
            mySids.type = datas.type
            presenter.saveToMy(mySids, 0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BindingHolder<ItemSidBinding> {
        val inflater: LayoutInflater = LayoutInflater.from(parent?.context)
        val binding: ItemSidBinding = ItemSidBinding.inflate(inflater, parent, false)
        return BindingHolder(binding)
    }

    fun updateItem(data: ArrayList<SidItemViewModel>) {
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
    //show My sids list views and hide catalog list views
    fun showMyIcons(isShow: Boolean) {
        this.isShow = isShow
        notifyDataSetChanged()
    }
    //set all checkBoxes in My sids list checked
    fun chackAll(isChacked: Boolean) {
        //this.isChacked = isChacked
        chackMap.entries.forEach {
            presenter.removeMySidById(it.key)
            val id = it.key
            val item = data.find { it.id == id }
            data.remove(item)
            (activity as MainActivity).removeDirectoty(it.value)
            //chackMap.remove(item?.id)
        }
        chackMap = HashMap()
        notifyDataSetChanged()
    }
    //set all checkBoxes in My sids list checked
    fun checkAllAndShowCheck(show: Boolean){
        this.isChacked = show
        this.showCheckBox = show
        data.forEachIndexed { index, addonItemViewModel ->
            chackMap.put(addonItemViewModel.id,addonItemViewModel.filePath!!)
        }
        listForJson.addAll(data)
        presenter.saveCheckedData(chackMap, SidJsonCreator(listForJson))
        notifyDataSetChanged()
    }
    //show checkBoxes in My sids list
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
        var list: ArrayList<SidItemViewModel> = arrayListOf()
        this.data = list
        notifyDataSetChanged()
    }
}