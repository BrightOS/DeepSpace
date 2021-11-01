package ru.myitschool.nasa_bootcamp.ui.spacex.explore.roadster

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.RoadsterModel
import ru.myitschool.nasa_bootcamp.utils.Status

interface RoadsterViewModel {
    suspend fun getRoadsterInfo()
    fun getViewModelScope(): CoroutineScope
    fun getRoadsterModel(): MutableLiveData<RoadsterModel>
    fun getStatus(): MutableLiveData<Status>
}
