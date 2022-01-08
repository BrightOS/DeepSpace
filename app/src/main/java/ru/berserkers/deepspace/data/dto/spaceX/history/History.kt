package ru.berserkers.deepspace.data.dto.spaceX.history

import com.google.gson.annotations.SerializedName
import ru.berserkers.deepspace.data.model.HistoryModel

class History(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("event_date_unix") val event_date_unix: Long,
    @field:SerializedName("details") val details: String
) {

    public fun createHistoryModel(): HistoryModel {
        return HistoryModel(id, title, event_date_unix, details)
    }
}
///Можно добавить ссылки на википедию/сайт spaceX
