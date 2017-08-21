package org.tlauncher.tlauncherpe.mc.datalayer.network.entity

import com.google.gson.annotations.SerializedName

data class MySkins(@SerializedName("id")
              var id: Int = 0,
              @SerializedName("name")
              var name: String? = null,
              @SerializedName("type")
              var type: Type? = null,
              @SerializedName("category")
              var category: Category? = null,
              @SerializedName("text")
              var text: String? = null,
              @SerializedName("date")
              var date: String? = null,
              @SerializedName("views")
              var views: String? = null,
              @SerializedName("downloads")
              var downloads: String? = null,
              @SerializedName("file_url")
              var file_url: String? = null,
              @SerializedName("file_size")
              var file_size: String? = null,
              @SerializedName("imgs")
              var imgs: List<String>? = null,
              @SerializedName("versions")
              var versions: Versions? = null,
              @SerializedName("tags")
              var tags: List<String>? = null,
              @SerializedName("filePath")
              var filePath: String? = null,
                   @SerializedName("isImported")
                   var isImported: Boolean? = false)