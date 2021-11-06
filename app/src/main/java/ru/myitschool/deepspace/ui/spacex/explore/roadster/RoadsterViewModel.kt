package ru.myitschool.deepspace.ui.spacex.explore.roadster

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.deepspace.data.model.RoadsterModel
import ru.myitschool.deepspace.utils.Status

interface RoadsterViewModel {
    suspend fun getRoadsterInfo()
    fun getViewModelScope(): CoroutineScope
    fun getRoadsterModel(): MutableLiveData<RoadsterModel>
    fun getStatus(): MutableLiveData<Status>
}
