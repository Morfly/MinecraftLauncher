package org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.morfly.cleanarchitecture.core.presentationlayer.adapter.BindingHolder
import com.squareup.picasso.Picasso
import org.tlauncher.tlauncherpe.mc.Constants
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.databinding.AllCategoryHeaderItemBinding
import org.tlauncher.tlauncherpe.mc.databinding.AllCategoryItemBinding
import org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory.AllCategoryContract
import org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory.AllCategoryItemViewModel

/**
 * Created by 85064 on 27.07.2017.
 */
class AllCategoryAdapter(var data : ArrayList<AllCategoryItemViewModel>, private val listener : View.OnClickListener,
                         var presenter: AllCategoryContract.Presenter, var context: Context, var activity: Activity) : RecyclerView.Adapter<BindingHolder<*>>() {

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BindingHolder<*>?, position: Int) {
        if (getItemViewType(position) == Constants.HEADER) {
            val bindingHolder = holder as BindingHolder<AllCategoryHeaderItemBinding>
            bindingHolder.binding.viewModel = data[position]
            if (data[position].headerName.equals(Constants.ADDON)){
                Picasso.with(context)
                        .load(R.drawable.ic_mods_selected)
                        .placeholder(R.drawable.ic_refresh)
                        .error(R.drawable.ic_refresh)
                        .into(holder.binding.headerImage)
                holder.binding.headerName.text = context.resources.getString(R.string.addons)
            }else if (data[position].headerName.equals(Constants.TEXTURE)){
                Picasso.with(context)
                        .load(R.drawable.ic_textures_selected)
                        .placeholder(R.drawable.ic_refresh)
                        .error(R.drawable.ic_refresh)
                        .into(holder.binding.headerImage)
                holder.binding.headerName.text = context.resources.getString(R.string.textures)
            }else if (data[position].headerName.equals(Constants.WORLD)){
                Picasso.with(context)
                        .load(R.drawable.ic_maps_selected)
                        .placeholder(R.drawable.ic_refresh)
                        .error(R.drawable.ic_refresh)
                        .into(holder.binding.headerImage)
                holder.binding.headerName.text = context.resources.getString(R.string.worlds)
            }else if (data[position].headerName.equals(Constants.SKIN)){
                Picasso.with(context)
                        .load(R.drawable.ic_skins_selected)
                        .placeholder(R.drawable.ic_refresh)
                        .error(R.drawable.ic_refresh)
                        .into(holder.binding.headerImage)
                holder.binding.headerName.text = context.resources.getString(R.string.skins)
            }else if (data[position].headerName.equals(Constants.SID)){
                Picasso.with(context)
                        .load(R.drawable.ic_sids_selected)
                        .placeholder(R.drawable.ic_refresh)
                        .error(R.drawable.ic_refresh)
                        .into(holder.binding.headerImage)
                holder.binding.headerName.text = context.resources.getString(R.string.sids)
            }
        } else {
            val bindingHolder = holder as BindingHolder<AllCategoryItemBinding>
            bindingHolder.binding.viewModel = data[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BindingHolder<*> {
        val inflater : LayoutInflater = LayoutInflater.from(parent!!.context)
        if (viewType == Constants.HEADER) {
            val itemBinding : AllCategoryHeaderItemBinding = AllCategoryHeaderItemBinding.inflate(inflater, parent, false)
            return BindingHolder(itemBinding)
        } else {
            val itemBinding : AllCategoryItemBinding = AllCategoryItemBinding.inflate(inflater, parent, false)
            return BindingHolder(itemBinding)
        }
    }

    override fun getItemViewType(position : Int) : Int {
        return if (data[position].type == Constants.HEADER) 1 else 2
    }
}