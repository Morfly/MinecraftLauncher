package org.tlauncher.tlauncherpe.mc.presentationlayer.explorer.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.morfly.cleanarchitecture.core.presentationlayer.adapter.BindingHolder
import org.tlauncher.tlauncherpe.mc.databinding.ItemExplorerBinding
import org.tlauncher.tlauncherpe.mc.presentationlayer.explorer.ExplorerContract
import java.io.File

class ExplorerAdapter(val data: List<ItemExplorer>, var presenter: ExplorerContract.Presenter,
                      val activity: Activity, val path: String/*, private val listener : View.OnClickListener*/ ) : RecyclerView.Adapter<BindingHolder<ItemExplorerBinding>>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BindingHolder<ItemExplorerBinding> {
        val binding = ItemExplorerBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        return BindingHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingHolder<ItemExplorerBinding>?, position: Int) {
        holder?.binding?.item = data[position]
        holder?.binding?.root?.setOnClickListener {
            val directory = File("$path/${holder.binding.folderName.text}")
            val contents = directory.listFiles()
            if (contents != null && contents.isNotEmpty() && directory.isDirectory) {
                presenter.returnPath("$path/${holder.binding.folderName.text}")
            }
        }
    }

    override fun getItemCount(): Int = data.size
}

