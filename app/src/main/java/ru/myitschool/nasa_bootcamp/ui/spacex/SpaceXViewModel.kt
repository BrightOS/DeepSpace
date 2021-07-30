package ru.myitschool.nasa_bootcamp.ui.spacex

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel

interface SpaceXViewModel {
    suspend fun loadSpaceXLaunches()
    fun getLaunchesList(): MutableLiveData<List<SxLaunchModel>>
    fun getViewModelScope(): CoroutineScope
}