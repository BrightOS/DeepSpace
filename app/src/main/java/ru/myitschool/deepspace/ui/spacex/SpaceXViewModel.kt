package ru.myitschool.deepspace.ui.spacex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.deepspace.data.model.SxLaunchModel
import ru.myitschool.deepspace.utils.Data
import ru.myitschool.deepspace.utils.Status

interface SpaceXViewModel {
    fun getSpaceXLaunches(): LiveData<Data<List<SxLaunchModel>>>
    fun getErrorHandler() : MutableLiveData<Status>
    fun getViewModelScope() : CoroutineScope
    suspend fun reconnectToSpaceX()
}
