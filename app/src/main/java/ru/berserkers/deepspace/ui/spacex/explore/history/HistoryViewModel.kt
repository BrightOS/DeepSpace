package ru.berserkers.deepspace.ui.spacex.explore.history

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.berserkers.deepspace.data.model.HistoryModel
import ru.berserkers.deepspace.utils.Status

interface HistoryViewModel {
    suspend fun getHistory()
    fun getViewModelScope(): CoroutineScope
    fun getHistoryList(): MutableLiveData<ArrayList<HistoryModel>>
    fun getStatus(): MutableLiveData<Status>
}