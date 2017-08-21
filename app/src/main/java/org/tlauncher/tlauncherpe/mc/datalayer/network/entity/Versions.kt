package org.tlauncher.tlauncherpe.mc.datalayer.network.entity

import com.google.gson.annotations.SerializedName

class Versions (@SerializedName("from")
                var from: Category? = null,
                @SerializedName("to")
                var to: Category? = null)