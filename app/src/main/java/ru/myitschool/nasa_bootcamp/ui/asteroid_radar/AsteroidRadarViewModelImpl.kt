package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.Asteroid
import ru.myitschool.nasa_bootcamp.data.model.AsteroidModel
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.data.repository.AsteroidRepository2
import ru.myitschool.nasa_bootcamp.ui.spacex.SpaceXViewModel
import javax.inject.Inject

@HiltViewModel
class AsteroidRadarViewModelImpl @Inject constructor(
    private val repository: AsteroidRepository2, beginDate: String, endDate: String
) : ViewModel(), SpaceXViewModel {

    var list: ArrayList<AsteroidModel> = arrayListOf()

    var listOfAsteroids: MutableLiveData<ArrayList<AsteroidModel>> =
        MutableLiveData<ArrayList<AsteroidModel>>()


    suspend fun getAsteroidList() {

        val response = repository.getAsteroids()


        for (asteroid in response.body()!!.near_earth_objects.date) {
            list.add(asteroid.createAsteroidModel())
            Log.d("Tag_AAA", asteroid.createAsteroidModel().name)

        }

        listOfAsteroids.value = list
    }
}