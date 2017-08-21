package org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory

import com.google.gson.annotations.SerializedName
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Category
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Type

/**
 * Created by 85064 on 26.07.2017.
 */
data class Data(@SerializedName("image")
                var image : List<String>? = arrayListOf(),
                @SerializedName("name")
                var name : String? = null,
                @SerializedName("downloads")
                var downloads : String? = null,
                @SerializedName("fileSize")
                var fileSize : Long? = 0,
                @SerializedName("id")
                var id : Int = 0,
                @SerializedName("date")
                var date: String? = null,
                @SerializedName("url")
                var url:String? = null,
                @SerializedName("loaded")
                var loaded: Boolean = false,
                @SerializedName("text")
                var text: String? = null,
                @SerializedName("views")
                var views: String? = null,
                @SerializedName("filePath")
                var filePath: String? = null,
                @SerializedName("type")
                var type: Type? = null,
                @SerializedName("category")
                var category: Category? = null,
                @SerializedName("isImported")
                var isImported: Boolean? = false)