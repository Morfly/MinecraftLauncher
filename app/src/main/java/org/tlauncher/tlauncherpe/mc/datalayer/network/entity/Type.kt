package org.tlauncher.tlauncherpe.mc.datalayer.network.entity

import com.google.gson.annotations.SerializedName

data class Type (@SerializedName("id")
            var id: Int = 0,
            @SerializedName("name")
            var name: String? = null)