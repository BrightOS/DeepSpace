package ru.myitschool.nasa_bootcamp.utils

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.myitschool.nasa_bootcamp.R
import java.util.*

class NotificationCentre {
    class NotificationReceiver : BroadcastReceiver() {
        private val channelId = "channel-id"
        private val channelName = "channel-name"
        private val notificationId = 0

        override fun onReceive(context: Context?, intent: Intent?) {
            createNotificationChannel(context!!)
            val notification = createNotification(context, intent!!)
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(notificationId, notification)
        }

        private fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    lightColor = Color.GREEN
                    enableLights(true)
                }
                val manager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }
        }

        private fun createNotification(context: Context, intent: Intent): Notification {
            return NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(intent.getStringExtra(titleIntent))
                .setContentText(intent.getStringExtra(textIntent))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()
        }

        companion object {
            const val titleIntent = "title"
            const val textIntent = "text"
            const val dateIntent = "date"
        }
    }

    fun scheduleNotification(context: Context) {
        println("It is$context")
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(NotificationReceiver.titleIntent, "Title")
        intent.putExtra(NotificationReceiver.textIntent, "Hey!")
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            System.currentTimeMillis() + 5000,
            pendingIntent
        )
    }
}