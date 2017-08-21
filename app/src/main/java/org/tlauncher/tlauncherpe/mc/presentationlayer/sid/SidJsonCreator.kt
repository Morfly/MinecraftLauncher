package org.tlauncher.tlauncherpe.mc.presentationlayer.sid

import org.json.JSONArray
import org.json.JSONObject
import java.util.*

fun SidJsonCreator(list: ArrayList<SidItemViewModel>): JSONArray {
    val array: JSONArray = JSONArray()
    list.forEachIndexed { index, sidItemViewModel ->
        val objectJSON: JSONObject = JSONObject()
        if (sidItemViewModel.category != null) {
            val categ: JSONObject = JSONObject()
            categ.put("id",sidItemViewModel.category.id)
            categ.put("name",sidItemViewModel.category.name)
            objectJSON.put("category",categ)
        }else{
            objectJSON.put("category",null)
        }
        objectJSON.put("date",sidItemViewModel.date)
        objectJSON.put("downloads",sidItemViewModel.downloads)
        objectJSON.put("filePath",sidItemViewModel.filePath)
        objectJSON.put("file_size",sidItemViewModel.fileSize)
        objectJSON.put("id",sidItemViewModel.id)
        //objectJSON.put("imgs",addonItemViewModel.image)
        //objectJSON.put("isImported",sidItemViewModel.isImported)
        objectJSON.put("loaded",sidItemViewModel.loaded)
        objectJSON.put("name",sidItemViewModel.name)
        objectJSON.put("text",sidItemViewModel.text)
        if (sidItemViewModel.type != null) {
            val type: JSONObject = JSONObject()
            type.put("id",sidItemViewModel.type.id)
            type.put("name",sidItemViewModel.type.name)
            objectJSON.put("type",type)
        }else{
            objectJSON.put("type", null)
        }
        objectJSON.put("file_url",sidItemViewModel.url)
        objectJSON.put("views",sidItemViewModel.views)
        array.put(objectJSON)
    }
    return array
}