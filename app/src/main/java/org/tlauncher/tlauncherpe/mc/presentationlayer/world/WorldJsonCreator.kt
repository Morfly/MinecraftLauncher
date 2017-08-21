package org.tlauncher.tlauncherpe.mc.presentationlayer.world

import org.json.JSONArray
import org.json.JSONObject
import java.util.*

fun WorldJsonCreator(list: ArrayList<WorldItemViewModel>): JSONArray {
    val array: JSONArray = JSONArray()
    list.forEachIndexed { index, worldItemViewModel ->
        val objectJSON: JSONObject = JSONObject()
        if (worldItemViewModel.category != null) {
            val categ: JSONObject = JSONObject()
            categ.put("id",worldItemViewModel.category.id)
            categ.put("name",worldItemViewModel.category.name)
            objectJSON.put("category",categ)
        }else{
            objectJSON.put("category",null)
        }
        objectJSON.put("date",worldItemViewModel.date)
        objectJSON.put("downloads",worldItemViewModel.downloads)
        objectJSON.put("filePath",worldItemViewModel.filePath)
        objectJSON.put("file_size",worldItemViewModel.fileSize)
        objectJSON.put("id",worldItemViewModel.id)
        //objectJSON.put("imgs",addonItemViewModel.image)
        objectJSON.put("loaded",worldItemViewModel.loaded)
        objectJSON.put("name",worldItemViewModel.name)
        objectJSON.put("text",worldItemViewModel.text)
        if (worldItemViewModel.type != null) {
            val type: JSONObject = JSONObject()
            type.put("id",worldItemViewModel.type.id)
            type.put("name",worldItemViewModel.type.name)
            objectJSON.put("type",type)
        }else{
            objectJSON.put("type", null)
        }
        objectJSON.put("file_url",worldItemViewModel.url)
        objectJSON.put("views",worldItemViewModel.views)
        array.put(objectJSON)
    }
    return array
}