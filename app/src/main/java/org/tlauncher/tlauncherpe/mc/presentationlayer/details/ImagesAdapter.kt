package org.tlauncher.tlauncherpe.mc.presentationlayer.details

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.morfly.cleanarchitecture.core.presentationlayer.adapter.BindingHolder
import org.tlauncher.tlauncherpe.mc.databinding.ImageItemBinding

class ImagesAdapter(val items : List<String>, val onPhotoClickListener : (String) -> Unit) : RecyclerView.Adapter<BindingHolder<ImageItemBinding>>() {
    override fun onCreateViewHolder(parent : ViewGroup?, viewType : Int) : BindingHolder<ImageItemBinding> {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent!!.context), parent, false)
        return BindingHolder(binding)
    }

    override fun onBindViewHolder(holder : BindingHolder<ImageItemBinding>?, position : Int) {
        holder!!.binding.photo = items[position]
        holder.binding.root.setOnClickListener { onPhotoClickListener.invoke(items[position]) }
    }

    override fun getItemCount() : Int = items.size
}