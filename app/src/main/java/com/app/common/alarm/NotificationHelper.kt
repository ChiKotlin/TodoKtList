package com.app.common.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.app.ui.R

@RequiresApi(Build.VERSION_CODES.O)
class NotificationHelper(base: Context?, private val title: String?) : ContextWrapper(base) {

    private val channelID = "channelID"
    private val channelName = "channel Name"

    private val notificationManager: NotificationManager =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }

    private fun createChannel() {
        val channel =
            NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
        getManager().createNotificationChannel(channel)
    }

    fun getManager(): NotificationManager {
        return notificationManager
    }

    fun getChannelNotification(): NotificationCompat.Builder? {
        val eggImage = BitmapFactory.decodeResource(
            applicationContext.resources, R.drawable.ic_todo
        )
        val bigPicStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(eggImage)
            .bigLargeIcon(null)

        return NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle(title)
            .setContentText("Ring Ring .. Ring Ring")
            .setSmallIcon(R.drawable.bell)
            .setStyle(bigPicStyle)
            .setAutoCancel(true)
            .setLargeIcon(eggImage)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
    }
}