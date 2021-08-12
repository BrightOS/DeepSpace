package ru.myitschool.nasa_bootcamp.ui.spacex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.utils.Data
import ru.myitschool.nasa_bootcamp.utils.EasyTimer
import ru.myitschool.nasa_bootcamp.utils.Status

interface SpaceXViewModel {
    fun getSpaceXLaunches(): LiveData<Data<List<SxLaunchModel>>>
    fun getErrorHandler() : MutableLiveData<Status>
    fun getViewModelScope() : CoroutineScope
    suspend fun reconnectToSpaceX()


}