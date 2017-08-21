package org.tlauncher.tlauncherpe.mc.presentationlayer.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import org.tlauncher.tlauncherpe.mc.presentationlayer.addon.AddonFragment
import org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory.AllCategoryFragment
import org.tlauncher.tlauncherpe.mc.presentationlayer.sid.SidFragment
import org.tlauncher.tlauncherpe.mc.presentationlayer.skin.SkinFragment
import org.tlauncher.tlauncherpe.mc.presentationlayer.texture.TextureFragment
import org.tlauncher.tlauncherpe.mc.presentationlayer.world.WorldFragment

class PagerAdapter(fm: FragmentManager, internal var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {


    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return AddonFragment()//AllCategoryFragment()
            1 -> return TextureFragment()
            2 -> return WorldFragment()
            3 -> return SkinFragment()
            4 -> return SidFragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}
