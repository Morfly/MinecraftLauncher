package org.tlauncher.tlauncherpe.mc

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast


object NetworkUtils {

    fun isNetworkConnected(context: Context, showErrorMessage: Boolean): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = cm.activeNetworkInfo
        if (ni == null) {
            if (showErrorMessage) {
                val message = context.resources.getString(R.string.error_no_internet)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
            return false
        } else
            return true
    }
}
