package ru.myitschool.nasa_bootcamp.ui.spacex.explore.history

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.CoreModel
import ru.myitschool.nasa_bootcamp.data.model.HistoryModel
import ru.myitschool.nasa_bootcamp.data.model.InfoModel

interface InfoViewModel {
    suspend fun getHistory()
    suspend fun getInfo()
    fun getViewModelScope(): CoroutineScope
    fun getHistoryList(): MutableLiveData<ArrayList<HistoryModel>>
    fun getInfoLiveData(): MutableLiveData<InfoModel>

}