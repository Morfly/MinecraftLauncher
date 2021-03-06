package org.tlauncher.tlauncherpe.mc.datalayer.network.response

import com.google.gson.annotations.SerializedName
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Textures

data class TexturesResponse (@SerializedName("error")
                             var error: Int = 0,
                             @SerializedName("action")
                             var action: String? = null,
                             @SerializedName("objects")
                             var objects: List<Textures>? = null,
                             @SerializedName("page")
                             var page: Int = 0)