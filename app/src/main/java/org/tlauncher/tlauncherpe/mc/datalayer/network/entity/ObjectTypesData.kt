package org.tlauncher.tlauncherpe.mc.datalayer.network.entity

import com.google.gson.annotations.SerializedName

class ObjectTypesData (@SerializedName("id")
                        var id: Int = 0,
                       @SerializedName("name")
                        var name: String? = null,
                       @SerializedName("categories")
                        var categories: List<Data>? = null,
                       @SerializedName("tags")
                        var tags: List<Data>? = null)