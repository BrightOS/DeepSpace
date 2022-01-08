package ru.berserkers.deepspace.ui.spacex.explore.history

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.berserkers.deepspace.data.model.InfoModel

interface InfoViewModel {
    suspend fun getInfo()
    fun getViewModelScope(): CoroutineScope
    fun getInfoLiveData(): MutableLiveData<InfoModel>
}
