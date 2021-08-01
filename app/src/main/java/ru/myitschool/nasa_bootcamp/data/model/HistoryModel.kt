package ru.myitschool.nasa_bootcamp.data.model

data class HistoryModel(
    val id: Int,
    val title: String,
    val event_date_utc: Int,
    val details: String
)