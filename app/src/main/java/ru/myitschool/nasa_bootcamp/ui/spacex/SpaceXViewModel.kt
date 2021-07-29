package ru.myitschool.nasa_bootcamp.ui.spacex

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel

interface SpaceXViewModel {
    suspend fun getSpaceXLaunches()
    fun getLaunchesList(): MutableLiveData<ArrayList<SxLaunchModel>>
    fun getViewModelScope(): CoroutineScope
}