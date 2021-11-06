package ru.myitschool.deepspace.ui.mars_rovers

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.deepspace.data.model.RoverModel

/*
 * @author Samuil Nalisin
 */
interface MarsRoversViewModel {
    suspend fun loadRoverPhotos()
    fun getRoverModelsLiveData() : MutableLiveData<ArrayList<RoverModel>>
    fun getViewModelScope(): CoroutineScope
}
