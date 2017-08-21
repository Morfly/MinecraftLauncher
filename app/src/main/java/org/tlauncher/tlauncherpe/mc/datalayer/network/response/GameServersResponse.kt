package org.tlauncher.tlauncherpe.mc.datalayer.network.response

import com.google.gson.annotations.SerializedName
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.Servers

class GameServersResponse(@SerializedName("error")
                          var error : Int = 0,
                          @SerializedName("action")
                          var action : String? = null,
                          @SerializedName("servers")
                          var servers : List<Servers>? = null)