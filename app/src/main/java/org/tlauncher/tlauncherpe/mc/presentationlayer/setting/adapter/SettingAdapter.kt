package org.tlauncher.tlauncherpe.mc.presentationlayer.setting.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import com.morfly.cleanarchitecture.core.presentationlayer.adapter.BindingHolder
import org.tlauncher.tlauncherpe.mc.*
import org.tlauncher.tlauncherpe.mc.databinding.ItemSettingBinding


class SettingAdapter(val data : List<ItemSetting>, val context: Context, val activity: Activity) : RecyclerView.Adapter<BindingHolder<ItemSettingBinding>>() {

    override fun getItemCount() : Int = data.size

    override fun onBindViewHolder(holder : BindingHolder<ItemSettingBinding>?, position : Int) {
        holder?.binding?.item = data[position]
        if (getLang(context) == 1) {
            holder?.binding?.lang?.text = context.resources.getString(R.string.ru)
        }else{
            holder?.binding?.lang?.text = context.resources.getString(R.string.en)
        }
        holder?.binding?.arrow?.setOnClickListener {
            val view = (activity.layoutInflater.inflate(R.layout.languages_view, null) as LinearLayout)
            val firstRadio = view.findViewById(R.id.ru) as RadioButton
            val secondRadio = view.findViewById(R.id.en) as RadioButton
            if (getLang(context) == 1){
                firstRadio.isChecked = true
            }else{
                secondRadio.isChecked = true
            }
            val customBuilder = AlertDialog.Builder(context)
                    .setTitle(context.resources.getString(R.string.change_lang))
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, { dialog, _ ->
                        run {
                            if (firstRadio.isChecked){
                                saveLang(1, context)
                                holder.binding.lang.text = context.resources.getString(R.string.ru)
                            }else{
                                saveLang(2, context)
                                holder.binding.lang.text = context.resources.getString(R.string.en)
                            }
                            val intent = activity.intent
                            activity.finish()
                            activity.startActivity(intent)
                            dialog.dismiss()
                        }
                    })
                    .setView(view)
            val dialog = customBuilder.create()
            dialog.show()
            // set green color in dialog button
            val negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
            negativeButton?.setTextColor(context.resources.getColor(R.color.progress_color))
            val positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            positiveButton?.setTextColor(context.resources.getColor(R.color.progress_color))
        }
        holder?.binding?.switchNot?.setOnClickListener {
            if (holder.binding.switchNot.isChecked){
                saveNotification(1,activity)
            }else{
                saveNotification(0,activity)
            }
        }
        holder?.binding?.checkBoxUpdates?.setOnClickListener {
            if (holder.binding.checkBoxUpdates.isChecked){
                saveUpdates(1,activity)
            }else{
                saveUpdates(0,activity)
            }
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup?, viewType : Int) : BindingHolder<ItemSettingBinding> {
        val binding = ItemSettingBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        return BindingHolder(binding)
    }
}