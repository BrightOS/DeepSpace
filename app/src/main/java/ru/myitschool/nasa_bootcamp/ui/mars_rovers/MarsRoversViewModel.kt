package ru.myitschool.nasa_bootcamp.ui.mars_rovers

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.RoverModel

interface MarsRoversViewModel {
    suspend fun loadRoverPhotos()
    fun getRoverModelsLiveData() : MutableLiveData<ArrayList<RoverModel>>
    fun getViewModelScope(): CoroutineScope

}