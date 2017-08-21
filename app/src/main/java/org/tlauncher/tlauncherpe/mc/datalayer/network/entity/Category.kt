package org.tlauncher.tlauncherpe.mc.datalayer.network.entity

import com.google.gson.annotations.SerializedName

class Category (@SerializedName("id")
                var id: String? = null,
                @SerializedName("name")
                var name: String? = null)