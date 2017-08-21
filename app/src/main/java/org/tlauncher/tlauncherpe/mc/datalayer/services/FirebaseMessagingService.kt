package org.tlauncher.tlauncherpe.mc.datalayer.services

import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.getNotification
import java.util.*


class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if (getNotification(applicationContext) == 1) {
            buildNotification(remoteMessage)
        }
    }

    private fun buildNotification(remoteMessage: RemoteMessage?) {
        val b = BitmapFactory.decodeResource(applicationContext.resources,R.drawable.tl_icon)
        val mBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.tl_icon)
                .setLargeIcon(b)
                .setContentTitle("TLauncher")
                .setContentText(remoteMessage?.data?.get("body") ?: "")
        val mNotifyMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotifyMgr.notify(Random().nextInt(), mBuilder.build())
    }
}
