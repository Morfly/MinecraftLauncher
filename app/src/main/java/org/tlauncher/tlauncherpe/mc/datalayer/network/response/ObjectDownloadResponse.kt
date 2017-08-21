package org.tlauncher.tlauncherpe.mc.datalayer.network.response

import com.google.gson.annotations.SerializedName

data class ObjectDownloadResponse (@SerializedName("error")
                                    var error: Int = 0,
                                   @SerializedName("error_text")
                                    var error_text: String? = null)