package com.berkay.a2dayswork.data.workmanager

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.berkay.a2dayswork.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val builder = NotificationCompat.Builder(context, "routineChannel")
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)
            .setContentTitle("Routine Reminder")
            .setContentText("It's time for your routine!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
            with(NotificationManagerCompat.from(context)) {
                notify(200, builder.build())
            }
        } else {
            // Handle the case where the permission is not granted. For example:
            builder.setVibrate(null)
            with(NotificationManagerCompat.from(context)) {
                notify(200, builder.build())
            }
        }
    }
}