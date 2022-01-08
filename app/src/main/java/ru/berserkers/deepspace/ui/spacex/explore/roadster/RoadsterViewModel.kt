package ru.berserkers.deepspace.ui.spacex.explore.roadster

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.berserkers.deepspace.data.model.RoadsterModel
import ru.berserkers.deepspace.utils.Status

interface RoadsterViewModel {
    suspend fun getRoadsterInfo()
    fun getViewModelScope(): CoroutineScope
    fun getRoadsterModel(): MutableLiveData<RoadsterModel>
    fun getStatus(): MutableLiveData<Status>
}
