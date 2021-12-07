package com.example.mvctodor.reciver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.mvctodor.R
import com.example.mvctodor.SeconActivity
import com.example.mvctodor.models.AppDatabase
import android.app.AlarmManager


class MyReciver : BroadcastReceiver() {

    private lateinit var appDatabase: AppDatabase

    override fun onReceive(context: Context, intent: Intent) {
        var id = intent.getIntExtra("id", 1)
        val intent = Intent(context, SeconActivity::class.java)

        intent.putExtra("id", id)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        appDatabase = AppDatabase.getInstance(context)

        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, 0)
        val CHANNEL_ID = "com.example.todoapp"
        val CHANNEL_NAME = "todoapp"

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle("title")
            .setContentText("body")
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "dec"
            notificationManager.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManager.notify(1, notification)
    }
}