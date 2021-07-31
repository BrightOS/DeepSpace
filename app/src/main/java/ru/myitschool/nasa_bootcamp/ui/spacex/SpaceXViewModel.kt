package ru.myitschool.nasa_bootcamp.ui.spacex

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.utils.ErrorHandler

interface SpaceXViewModel {
    suspend fun loadSpaceXLaunches()
    fun getLaunchesList(): MutableLiveData<List<SxLaunchModel>>
    fun getViewModelScope(): CoroutineScope
    fun getErrorHandler() : MutableLiveData<ErrorHandler>
}