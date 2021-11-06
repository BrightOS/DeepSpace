package ru.myitschool.deepspace.ui.spacex.explore.history

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.deepspace.data.model.InfoModel

interface InfoViewModel {
    suspend fun getInfo()
    fun getViewModelScope(): CoroutineScope
    fun getInfoLiveData(): MutableLiveData<InfoModel>
}
