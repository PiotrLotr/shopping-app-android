package com.example.shoppingapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class GeofenceService : Service() {

    // class variables:
    private val channelName = "channel_name"
    private val channelID = 1
    private val notificationChannelDescription = "notification_description"
    var id = 0
    val requestCode = 1

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val launchIntent = Intent(intent)
        launchIntent.component = ComponentName(
            "com.example.shoppingapp",
            "com.example.shoppingapp.LocationListActivity"
        )

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            requestCode,
            launchIntent,
            PendingIntent.FLAG_ONE_SHOT
        )
        // generating notification
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, channelID.toString())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("GEOFENCE ADDED: ")
            .setContentText("Click to edit...")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(id++, notification)

        return START_STICKY
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return
        val notificationChannel = NotificationChannel(
            channelID.toString(),
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.description = notificationChannelDescription
        val  notificationManager  = NotificationManagerCompat.from(this)
        notificationManager.createNotificationChannel(notificationChannel)
    }

}