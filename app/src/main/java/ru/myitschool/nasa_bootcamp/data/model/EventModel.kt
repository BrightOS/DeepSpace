package ru.myitschool.nasa_bootcamp.data.model


data class EventModel(
    val id: String? = null,
    val title: String,
    val description: String? = null,
    val link: String? = null,
    val isClosed: Boolean? = null
)
