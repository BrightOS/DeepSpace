package ru.myitschool.nasa_bootcamp.ui.spacex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.utils.Data
import ru.myitschool.nasa_bootcamp.utils.Status

interface SpaceXViewModel {
    suspend fun loadSpaceXLaunches()
    //fun getSpaceXLaunches(): LiveData<Data<List>>
    fun getLaunchesList(): MutableLiveData<List<SxLaunchModel>>
    fun getViewModelScope(): CoroutineScope
    fun getErrorHandler() : MutableLiveData<Status>
}