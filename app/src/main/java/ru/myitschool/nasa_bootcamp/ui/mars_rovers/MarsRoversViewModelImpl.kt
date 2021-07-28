package ru.myitschool.nasa_bootcamp.ui.mars_rovers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import ru.myitschool.nasa_bootcamp.data.model.RoverModel
import ru.myitschool.nasa_bootcamp.data.repository.NasaRepository
import ru.myitschool.nasa_bootcamp.ui.asteroid_radar.AsteroidRadarViewModel
import javax.inject.Inject

@HiltViewModel
class MarsRoversViewModelImpl @Inject constructor(
    private val repository: NasaRepository
) : ViewModel(), MarsRoversViewModel {

     var roverModels: MutableLiveData<ArrayList<RoverModel>> =
        MutableLiveData<ArrayList<RoverModel>>()

    var list: ArrayList<RoverModel> = arrayListOf()

    override suspend fun loadRoverPhotos() {
        val response = repository.getRoverPhotos()

        if (response.isSuccessful) {
            if (response.body() != null) {
                for (r in response.body()!!.photos) {
                    list.add(r.createRoverModel())
                }
            }
        }
      //  list.reverse()
        roverModels.value =  list

        for (r in roverModels.value!!)
            Log.d("CHTO", "" + r.id + " " + r.img_src)
    }

    override fun getRoverModelsLiveData(): MutableLiveData<ArrayList<RoverModel>> {
        return roverModels
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope
}