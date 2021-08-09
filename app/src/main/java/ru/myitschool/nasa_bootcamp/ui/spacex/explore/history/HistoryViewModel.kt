package ru.myitschool.nasa_bootcamp.ui.spacex.explore.history

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.HistoryModel
import ru.myitschool.nasa_bootcamp.utils.Status

interface HistoryViewModel {
    suspend fun getHistory()
    fun getViewModelScope(): CoroutineScope
    fun getHistoryList(): MutableLiveData<ArrayList<HistoryModel>>
    fun getStatus(): MutableLiveData<Status>

}