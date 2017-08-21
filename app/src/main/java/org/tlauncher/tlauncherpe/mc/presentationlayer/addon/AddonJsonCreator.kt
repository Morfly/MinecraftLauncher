package org.tlauncher.tlauncherpe.mc.presentationlayer.addon

import org.json.JSONArray
import org.json.JSONObject
import java.util.*

fun AddonJsonCreator(list: ArrayList<AddonItemViewModel>): JSONArray {
    val array: JSONArray = JSONArray()
    list.forEachIndexed { index, addonItemViewModel ->
        val objectJSON: JSONObject = JSONObject()
        if (addonItemViewModel.category != null) {
            val categ: JSONObject = JSONObject()
            categ.put("id",addonItemViewModel.category.id)
            categ.put("name",addonItemViewModel.category.name)
            objectJSON.put("category",categ)
        }else{
            objectJSON.put("category",null)
        }
        objectJSON.put("date",addonItemViewModel.date)
        objectJSON.put("downloads",addonItemViewModel.downloads)
        objectJSON.put("filePath",addonItemViewModel.filePath)
        objectJSON.put("file_size",addonItemViewModel.fileSize)
        objectJSON.put("id",addonItemViewModel.id)
        //objectJSON.put("imgs",addonItemViewModel.image)
        objectJSON.put("isImported",addonItemViewModel.isImported)
        objectJSON.put("loaded",addonItemViewModel.loaded)
        objectJSON.put("name",addonItemViewModel.name)
        objectJSON.put("text",addonItemViewModel.text)
        if (addonItemViewModel.type != null) {
            val type: JSONObject = JSONObject()
            type.put("id",addonItemViewModel.type.id)
            type.put("name",addonItemViewModel.type.name)
            objectJSON.put("type",type)
        }else{
            objectJSON.put("type", null)
        }
        objectJSON.put("file_url",addonItemViewModel.url)
        objectJSON.put("views",addonItemViewModel.views)
        array.put(objectJSON)
    }
    return array
}