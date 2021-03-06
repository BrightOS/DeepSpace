package ru.berserkers.deepspace.ui.spacex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.berserkers.deepspace.data.model.SxLaunchModel
import ru.berserkers.deepspace.utils.Data
import ru.berserkers.deepspace.utils.Status

interface SpaceXViewModel {
    fun getSpaceXLaunches(): LiveData<Data<List<SxLaunchModel>>>
    fun getErrorHandler() : MutableLiveData<Status>
    fun getViewModelScope() : CoroutineScope
    suspend fun reconnectToSpaceX()
}
