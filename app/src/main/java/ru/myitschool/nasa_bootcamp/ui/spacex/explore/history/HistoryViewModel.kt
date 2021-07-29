package ru.myitschool.nasa_bootcamp.ui.spacex.explore.history

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.CoreModel
import ru.myitschool.nasa_bootcamp.data.model.HistoryModel

interface HistoryViewModel {
    suspend fun getHistory()
    fun getViewModelScope(): CoroutineScope
    fun getHistoryList(): MutableLiveData<ArrayList<HistoryModel>>
}