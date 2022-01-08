package ru.berserkers.deepspace.data.model

data class NotificationModel(
    val title: String,
    val text: String,
    val date: Long,
    val launchModel: UpcomingLaunchModel,
    val requestCode: Int
    )
