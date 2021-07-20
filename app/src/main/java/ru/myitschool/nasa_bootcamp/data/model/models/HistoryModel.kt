package ru.myitschool.nasa_bootcamp.data.model.models

data class HistoryModel(
    val id: Int,
    val title: String,
    val launch_year: Int,
    val event_date_utc: String,
    val details: String
)