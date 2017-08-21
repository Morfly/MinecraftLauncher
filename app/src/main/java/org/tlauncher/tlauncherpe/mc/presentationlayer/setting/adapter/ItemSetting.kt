package org.tlauncher.tlauncherpe.mc.presentationlayer.setting.adapter

import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import android.view.View
import org.tlauncher.tlauncherpe.mc.R

class ItemSetting(val title : String, val type : Type, val active : ObservableBoolean) {
    val arrowVisibility = ObservableInt(if (type == Type.Arrow) View.VISIBLE else View.GONE)
    val switchVisibility = ObservableInt(if (type == Type.Switch) View.VISIBLE else View.GONE)
    val customSwitchVisibility = ObservableInt(if (type == Type.CustomSwitch) View.VISIBLE else View.GONE)
    val checkboxVisibility = ObservableInt(if (type == Type.Checkbox) View.VISIBLE else View.GONE)
    val checkedButton = ObservableInt(if (active.get()) R.id.creation else R.id.survive)

    enum class Type {
        Arrow, Switch, CustomSwitch, Checkbox
    }

    fun isClickable() : Boolean = type == Type.Arrow
}