package com.app.common.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.app.common.utils.AppConstants
import kotlin.random.Random

class AlarmReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationHelper =
            NotificationHelper(context, intent?.getStringExtra(AppConstants.NOTIF_TITLE))
        val nb: NotificationCompat.Builder? = notificationHelper.getChannelNotification()
        notificationHelper.getManager().notify(Random.nextInt(), nb!!.build())
    }
}