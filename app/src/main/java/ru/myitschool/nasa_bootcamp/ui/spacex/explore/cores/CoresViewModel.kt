package ru.myitschool.nasa_bootcamp.ui.spacex.explore.cores

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.CapsuleModel
import ru.myitschool.nasa_bootcamp.data.model.CoreModel
import ru.myitschool.nasa_bootcamp.utils.Status

interface CoresViewModel {
    suspend fun getCores()
    fun getViewModelScope(): CoroutineScope
    fun getCoresList(): MutableLiveData<ArrayList<CoreModel>>
    fun getStatus(): MutableLiveData<Status>

}