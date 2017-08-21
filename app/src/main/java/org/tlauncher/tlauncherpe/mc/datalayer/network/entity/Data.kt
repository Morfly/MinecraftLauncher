package org.tlauncher.tlauncherpe.mc.datalayer.network.entity

import com.google.gson.annotations.SerializedName

class Data (@SerializedName("id")
            var id: String? = null,
            @SerializedName("name")
            var name: String? = null,
            @SerializedName("rank")
            var rank: String? = null)