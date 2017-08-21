package org.tlauncher.tlauncherpe.mc.datalayer.network.entity

import com.google.gson.annotations.SerializedName

class Servers (@SerializedName("id")
                var id: Int = 0,
               @SerializedName("name")
                var name: String? = null,
               @SerializedName("text")
                var text: String? = null,
                @SerializedName("ip")
                var ip: String? = null,
               @SerializedName("rank")
                var rank: Int = 0,
               @SerializedName("versions")
                var versions: Versions? = null)