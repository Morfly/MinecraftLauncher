package org.tlauncher.tlauncherpe.mc.presentationlayer.server.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.morfly.cleanarchitecture.core.presentationlayer.adapter.BindingHolder
import org.tlauncher.tlauncherpe.mc.databinding.ItemServersBinding
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.ServerActivity
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.ServerContract
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.ServerItemViewModel

class ServerAdapter(var listServerItems : ArrayList<ServerItemViewModel>, val listener : View.OnClickListener,
                    var presenter : ServerContract.Presenter, var activity: Activity) : RecyclerView.Adapter<BindingHolder<ItemServersBinding>>() {

    // get size from list data
    override fun getItemCount() : Int = listServerItems.size

    override fun onBindViewHolder(holder : BindingHolder<ItemServersBinding>?, position : Int) {
        holder?.binding?.viewModel = listServerItems[position]
        holder?.binding?.root?.tag = position
        holder?.binding?.presenter = presenter
        holder?.binding?.root?.setOnClickListener(listener)

        // set click listener on button server on off
        holder?.binding?.serverOnOff?.setOnClickListener {
            (activity as ServerActivity).fabButtonClick()
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup?, viewType : Int) : BindingHolder<ItemServersBinding> {
        val inflater : LayoutInflater = LayoutInflater.from(parent?.context)
        val binding : ItemServersBinding = ItemServersBinding.inflate(inflater, parent, false)
        return BindingHolder(binding)
    }

    fun updateItem(listServerItems: ArrayList<ServerItemViewModel>) {
        this.listServerItems = listServerItems
        notifyDataSetChanged()
    }
}