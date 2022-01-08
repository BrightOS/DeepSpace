package ru.berserkers.deepspace.data.model

/*
 * @author Yana Glad
 */
data class HistoryModel(
    val id: Int,
    val title: String,
    val event_date_utc: Long,
    val details: String
)
