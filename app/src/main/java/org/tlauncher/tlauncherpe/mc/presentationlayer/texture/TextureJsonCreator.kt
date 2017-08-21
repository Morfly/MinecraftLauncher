package org.tlauncher.tlauncherpe.mc.presentationlayer.texture

import org.json.JSONArray
import org.json.JSONObject
import java.util.*

fun TextureJsonCreator(list: ArrayList<TextureItemViewModel>): JSONArray {
    val array: JSONArray = JSONArray()
    list.forEachIndexed { index, textureItemViewModel ->
        val objectJSON: JSONObject = JSONObject()
        if (textureItemViewModel.category != null) {
            val categ: JSONObject = JSONObject()
            categ.put("id",textureItemViewModel.category.id)
            categ.put("name",textureItemViewModel.category.name)
            objectJSON.put("category",categ)
        }else{
            objectJSON.put("category",null)
        }
        objectJSON.put("date",textureItemViewModel.date)
        objectJSON.put("downloads",textureItemViewModel.downloads)
        objectJSON.put("filePath",textureItemViewModel.filePath)
        objectJSON.put("file_size",textureItemViewModel.fileSize)
        objectJSON.put("id",textureItemViewModel.id)
        //objectJSON.put("imgs",addonItemViewModel.image)
        objectJSON.put("isImported",textureItemViewModel.isImported)
        objectJSON.put("loaded",textureItemViewModel.loaded)
        objectJSON.put("name",textureItemViewModel.name)
        objectJSON.put("text",textureItemViewModel.text)
        if (textureItemViewModel.type != null) {
            val type: JSONObject = JSONObject()
            type.put("id",textureItemViewModel.type.id)
            type.put("name",textureItemViewModel.type.name)
            objectJSON.put("type",type)
        }else{
            objectJSON.put("type", null)
        }
        objectJSON.put("file_url",textureItemViewModel.url)
        objectJSON.put("views",textureItemViewModel.views)
        array.put(objectJSON)
    }
    return array
}