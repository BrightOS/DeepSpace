package ru.myitschool.nasa_bootcamp.utils

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.myitschool.nasa_bootcamp.MainActivity
import ru.myitschool.nasa_bootcamp.R
import java.text.SimpleDateFormat
import java.util.*

class NotificationCentre {
    fun scheduleNotification(context: Context, title: String, text: String, date: String) {
        println(parseDate(date) - Date().time)
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(NotificationReceiver.titleIntent, title)
        intent.putExtra(NotificationReceiver.textIntent, text)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + (parseDate(date) - Date().time),
            pendingIntent
        )
    }

    private fun parseDate(date: String): Long {
        return SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale("ru", "RU")).parse(date)!!.time
    }

    class NotificationReceiver : BroadcastReceiver() {
        private val channelId = "channel-id"
        private val channelName = "channel-name"
        private val notificationId = 0

        override fun onReceive(context: Context?, intent: Intent?) {
            println("Its nice!")
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
            val toDoAfterClick = Intent(context, MainActivity::class.java)
            val pendingIntent = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(toDoAfterClick)
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            return NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.deep_space_icon)
                .setContentTitle(intent.getStringExtra(titleIntent))
                .setContentText(intent.getStringExtra(textIntent))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()
        }

        companion object {
            const val titleIntent = "title"
            const val textIntent = "text"
            const val dateIntent = "date"
        }
    }
}