package com.instechrx.gitnabilapp.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.instechrx.gitnabilapp.ui.FavoriteActivity
import com.instechrx.gitnabilapp.R


class MyReceiver : BroadcastReceiver() {
    companion object {
        private const val NOTIFICATION_ID = 101
        private const val CHANNEL_ID = "Channel_1"
        const val CHANNEL_NAME = "Daily Reminder Channel"
    }
    override fun onReceive(context: Context, intent: Intent?) {
        val dailyIntent = Intent(context, FavoriteActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            dailyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        Log.d("daily", "Daily Work")
        val dailyNotif =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val dailyBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_logo)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.mipmap.ic_logo
                )
            )
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentTitle(context.resources.getString(R.string.title_daily_reminder))
            .setContentText(context.resources.getString(R.string.msg_daily))
            .setSubText(context.resources.getString(R.string.msg_daily_reminder))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notification = dailyBuilder.build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            dailyBuilder.setChannelId(CHANNEL_ID)
            dailyNotif.createNotificationChannel(channel)
        }
        dailyNotif.notify(NOTIFICATION_ID, notification)
    }

}