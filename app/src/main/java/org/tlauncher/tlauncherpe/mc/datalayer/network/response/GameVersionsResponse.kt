package org.tlauncher.tlauncherpe.mc.datalayer.network.response

import com.google.gson.annotations.SerializedName
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Data

data class GameVersionsResponse(@SerializedName("error")
                         var error: Int = 0,
                                @SerializedName("action")
                         var action: String? = null,
                                @SerializedName("data")
                            var data: List<Data>? = null)