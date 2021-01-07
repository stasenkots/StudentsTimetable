package com.stasenkots.studentstimetable.notification

import android.app.Notification.DEFAULT_VIBRATE
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.SyncStateContract
import androidx.core.app.NotificationCompat
import com.stasenkots.studentstimetable.R
import com.stasenkots.studentstimetable.constants.AppConstants.NOTIFICATION_CHANNEL_ID
import com.stasenkots.studentstimetable.ui.MainActivity

class NotificationTimeTableManager {
    fun notify(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =  PendingIntent.getActivity(context, 0, intent, 0)
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.there_are_changes))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(1, notification)
    }
}