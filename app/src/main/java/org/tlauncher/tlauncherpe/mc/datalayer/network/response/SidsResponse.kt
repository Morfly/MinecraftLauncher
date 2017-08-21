package org.tlauncher.tlauncherpe.mc.datalayer.network.response

import com.google.gson.annotations.SerializedName
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Sids

data class SidsResponse(@SerializedName("error")
                        var error: Int = 0,
                        @SerializedName("action")
                        var action: String? = null,
                        @SerializedName("objects")
                        var objects: List<Sids>? = null,
                        @SerializedName("page")
                        var page: Int = 0)