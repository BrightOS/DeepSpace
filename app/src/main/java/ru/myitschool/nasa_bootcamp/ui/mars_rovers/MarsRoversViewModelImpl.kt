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
import java.lang.Exception
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MarsRoversViewModelImpl @Inject constructor(
    private val repository: NasaRepository
) : ViewModel(), MarsRoversViewModel {

    var roverModels: MutableLiveData<ArrayList<RoverModel>> =
        MutableLiveData<ArrayList<RoverModel>>()

    var list: ArrayList<RoverModel> = arrayListOf()

    fun createRandomSol(): Int {
        val solValues = arrayListOf<Int>(2000, 1000, 2428, 2426)
        return Random.nextInt(solValues.size)
    }


    //TODO: move to interactor + use cases
    override suspend fun loadRoverPhotos() {
        try {
            val rnd = createRandomSol()
            val response = repository.getRoverCuriosityPhotos(createRandomSol())
            if (response.isSuccessful)
                if (response.body() != null)
                    for (r in response.body()!!.photos)
                        list.add(r.createRoverModel())

            val response2 = repository.getRoverOpportunityPhotos(2000)
            val response3 = repository.getRoverSpiritPhotos(1000)

            if (response2.isSuccessful)
                if (response2.body() != null)
                    for (r in response2.body()!!.photos)
                        list.add(r.createRoverModel())

            if (response3.isSuccessful)
                if (response3.body() != null)
                    for (r in response3.body()!!.photos)
                        list.add(r.createRoverModel())

            list.shuffle()
            roverModels.value = list

            for (r in roverModels.value!!)
                Log.d("CHTO", "" + r.id + " " + r.img_src)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getRoverModelsLiveData(): MutableLiveData<ArrayList<RoverModel>> {
        return roverModels
    }

    override fun getViewModelScope(): CoroutineScope = viewModelScope
}