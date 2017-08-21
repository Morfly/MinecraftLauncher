package org.tlauncher.tlauncherpe.mc.datalayer.network.response

import com.google.gson.annotations.SerializedName

/**
 * Created by 85064 on 17.07.2017.
 */
class SaveTokenResponse(@SerializedName("error")
                        var error : Int = 0,
                        @SerializedName("error_text")
                        var error_text : String? = null)