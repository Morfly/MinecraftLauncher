package org.tlauncher.tlauncherpe.mc.presentationlayer.setting

import android.content.res.Configuration
import android.databinding.ObservableBoolean
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.getLang
import org.tlauncher.tlauncherpe.mc.getNotification
import org.tlauncher.tlauncherpe.mc.getUpdates
import org.tlauncher.tlauncherpe.mc.presentationlayer.setting.adapter.ItemSetting
import org.tlauncher.tlauncherpe.mc.presentationlayer.setting.adapter.SettingAdapter
import java.util.*

class FragmentSetting : Fragment() {
    override fun onCreateView(inflater : LayoutInflater?, container : ViewGroup?, savedInstanceState : Bundle?) : View? {
        if (getLang(activity) == 1){
            changeLanguage("ru")
        }else{
            changeLanguage("en")
        }
        val list = RecyclerView(context)
        list.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        list.layoutManager = LinearLayoutManager(context)
        val decorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(context, R.drawable.list_divider))
        list.addItemDecoration(decorator)
        list.adapter = SettingAdapter(createSettings(), activity, activity)
        activity.setTitle(R.string.menu_settings)
        return list
    }

    fun createSettings() : List<ItemSetting> {
        val data = ArrayList<ItemSetting>()
        data.add(ItemSetting(context.resources.getString(R.string.lang), ItemSetting.Type.Arrow, ObservableBoolean(true)))
        if (getNotification(activity) == 0) {
            data.add(ItemSetting(context.resources.getString(R.string.notification), ItemSetting.Type.Switch, ObservableBoolean(false)))
        }else{
            data.add(ItemSetting(context.resources.getString(R.string.notification), ItemSetting.Type.Switch, ObservableBoolean(true)))
        }
        if (getUpdates(activity) == 0){
            data.add(ItemSetting(context.resources.getString(R.string.check_updates), ItemSetting.Type.Checkbox, ObservableBoolean(false)))
        }else{
            data.add(ItemSetting(context.resources.getString(R.string.check_updates), ItemSetting.Type.Checkbox, ObservableBoolean(true)))
        }
        //data.add(ItemSetting("Test 3", ItemSetting.Type.CustomSwitch, ObservableBoolean(false)))

        return data
    }

    fun changeLanguage(language: String) {
        /**
         * Change language
         */
        if (language.equals("", ignoreCase = true)) {
            return
        }

        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)

        activity.resources
                .updateConfiguration(configuration, activity
                        .resources.displayMetrics)
    }
}
