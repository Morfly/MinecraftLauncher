package org.tlauncher.tlauncherpe.mc.presentationlayer.details.photopreview

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import org.tlauncher.tlauncherpe.mc.databinding.PhotoItemBinding

class PhotoAdapter(val data : List<String>) : PagerAdapter() {
    override fun isViewFromObject(view : View?, item : Any?) : Boolean {
        return view == item as RelativeLayout
    }

    override fun getCount() : Int = data.size

    override fun instantiateItem(container : ViewGroup?, position : Int) : Any {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(container?.context), container, false)
        binding.photo = data[position]
        container?.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container : ViewGroup?, position : Int, `object` : Any?) {
        container?.removeView(`object` as RelativeLayout)
    }
}