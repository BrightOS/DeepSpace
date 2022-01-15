package ru.berserkers.deepspace.utils

import android.content.Context
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import com.microsoft.windowsazure.messaging.notificationhubs.NotificationListener

class AzureNotificationListener : NotificationListener {
    override fun onPushNotificationReceived(p0: Context?, p1: RemoteMessage?) {
        val notification: RemoteMessage.Notification = p1?.notification!!
        val title: String? = notification.title
        val body: String? = notification.body
        val data: Map<String, String> = p1.data

        Log.d(TAG, "Message Notification Title: $title")
        Log.d(TAG, "Message Notification Body: $p1")

        for ((key, value) in data) {
            Log.d(TAG, "key, $key value $value")
        }
    }
}