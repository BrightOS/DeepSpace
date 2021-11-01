package ru.myitschool.nasa_bootcamp.ui.spacex.explore.history

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.InfoModel

interface InfoViewModel {
    suspend fun getInfo()
    fun getViewModelScope(): CoroutineScope
    fun getInfoLiveData(): MutableLiveData<InfoModel>
}
