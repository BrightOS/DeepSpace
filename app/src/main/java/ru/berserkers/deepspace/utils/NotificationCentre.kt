package ru.berserkers.deepspace.utils

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.berserkers.deepspace.MainActivity
import ru.berserkers.deepspace.R
import ru.berserkers.deepspace.data.model.NotificationModel
import ru.berserkers.deepspace.data.model.UpcomingLaunchModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotificationCentre {
    private val sharedPreferencesFileName = "savedNotifications"
    private val sharedPreferencesTableName = "notifs"

    fun scheduleNotification(
        context: Context,
        title: String,
        text: String,
        date: String,
        launchModel: UpcomingLaunchModel,
    ): NotificationModel {
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(NotificationReceiver.titleIntent, title)
        intent.putExtra(NotificationReceiver.textIntent, text)
        val dateAlarm = parseDate(date)
        println(Date(dateAlarm).toString())

        // getting last request code so we will be able to cancel scheduled notification
        val lastRequestCode: Int = try {
            getAllScheduledNotifications(context).last().requestCode + 1
        } catch (e: Exception) {
            0
        }
        val notificationModel = NotificationModel(title, text, dateAlarm, launchModel, lastRequestCode)
        saveNotification(context, notificationModel)

        val pendingIntent =
            PendingIntent.getBroadcast(context, lastRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + (dateAlarm - Date().time),
            pendingIntent
        )
        return notificationModel
    }

    fun cancelNotification(context: Context, notification: NotificationModel) {
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(NotificationReceiver.titleIntent, notification.title)
        intent.putExtra(NotificationReceiver.textIntent, notification.text)

        deleteNotification(context, notification)
        val pendingIntent =
            PendingIntent.getBroadcast(context, notification.requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    fun getAllScheduledNotifications(context: Context): ArrayList<NotificationModel> {
        val sharedPreferences =
            context.getSharedPreferences(sharedPreferencesFileName, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(sharedPreferencesTableName, null)
        val type = object : TypeToken<ArrayList<NotificationModel>>() {}.type
        val notifications = Gson().fromJson<ArrayList<NotificationModel>>(json, type)
        return notifications ?: ArrayList()
    }

    private fun deleteNotification(context: Context, notification: NotificationModel) {
        val sharedPreferencesEditable =
            context.getSharedPreferences(sharedPreferencesFileName, Context.MODE_PRIVATE).edit()
        val allNotifications = getAllScheduledNotifications(context)
        allNotifications.remove(notification)
        val json = Gson().toJson(allNotifications)
        sharedPreferencesEditable.putString(sharedPreferencesTableName, json)
        sharedPreferencesEditable.apply()
    }

    private fun parseDate(date: String): Long {
        return SimpleDateFormat("MMMM d. yyyy. hh:mm", Locale.US).parse(date)!!.time
    }

    private fun saveNotification(context: Context, notification: NotificationModel) {
        val sharedPreferencesEditable: SharedPreferences.Editor =
            context.getSharedPreferences(sharedPreferencesFileName, Context.MODE_PRIVATE).edit()
        val allNotifications = getAllScheduledNotifications(context)
        allNotifications.add(notification)
        val json = Gson().toJson(allNotifications)
        sharedPreferencesEditable.putString(sharedPreferencesTableName, json)
        sharedPreferencesEditable.apply()
    }

    class NotificationReceiver : BroadcastReceiver() {
        private val channelId = "channel-id"
        private val channelName = "Upcoming events"
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
