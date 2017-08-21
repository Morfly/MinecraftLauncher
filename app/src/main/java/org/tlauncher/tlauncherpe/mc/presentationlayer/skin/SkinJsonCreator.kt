package org.tlauncher.tlauncherpe.mc.presentationlayer.skin

import org.json.JSONArray
import org.json.JSONObject
import java.util.*

fun SkinJsonCreator(list: ArrayList<SkinItemViewModel>): JSONArray {
    val array: JSONArray = JSONArray()
    list.forEachIndexed { index, skinItemViewModel ->
        val objectJSON: JSONObject = JSONObject()
        if (skinItemViewModel.category != null) {
            val categ: JSONObject = JSONObject()
            categ.put("id",skinItemViewModel.category.id)
            categ.put("name",skinItemViewModel.category.name)
            objectJSON.put("category",categ)
        }else{
            objectJSON.put("category",null)
        }
        objectJSON.put("date",skinItemViewModel.date)
        objectJSON.put("downloads",skinItemViewModel.downloads)
        objectJSON.put("filePath",skinItemViewModel.filePath)
        objectJSON.put("file_size",skinItemViewModel.fileSize)
        objectJSON.put("id",skinItemViewModel.id)
        //objectJSON.put("imgs",addonItemViewModel.image)
        objectJSON.put("isImported",skinItemViewModel.isImported)
        objectJSON.put("loaded",skinItemViewModel.loaded)
        objectJSON.put("name",skinItemViewModel.name)
        objectJSON.put("text",skinItemViewModel.text)
        if (skinItemViewModel.type != null) {
            val type: JSONObject = JSONObject()
            type.put("id",skinItemViewModel.type.id)
            type.put("name",skinItemViewModel.type.name)
            objectJSON.put("type",type)
        }else{
            objectJSON.put("type", null)
        }
        objectJSON.put("file_url",skinItemViewModel.url)
        objectJSON.put("views",skinItemViewModel.views)
        array.put(objectJSON)
    }
    return array
}