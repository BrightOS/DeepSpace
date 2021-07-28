package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.AsteroidModel

interface AsteroidRadarViewModel {
    suspend fun getAsteroidList()
    fun getAsteroidListViewModel() : MutableLiveData<ArrayList<AsteroidModel>>
    fun getViewModelScope(): CoroutineScope
}