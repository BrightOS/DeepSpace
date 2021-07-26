package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.Asteroid
import ru.myitschool.nasa_bootcamp.data.model.AsteroidModel
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.data.repository.AsteroidRepository2
import ru.myitschool.nasa_bootcamp.ui.spacex.SpaceXViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class AsteroidRadarViewModelImpl @Inject constructor(
    private val repository: AsteroidRepository2
) : ViewModel(), SpaceXViewModel {

    var list: ArrayList<AsteroidModel> = arrayListOf()

    var listOfAsteroids: MutableLiveData<ArrayList<AsteroidModel>> =
        MutableLiveData<ArrayList<AsteroidModel>>()


    suspend fun getAsteroidList() {
        Log.d("Tag_AAA", "Today : ${getPlusSevenDaysDateFormatted()}")


        val response = repository.getAsteroids("2015-09-07","2015-09-08")

        Log.d("Tag_AAA", "OBJ : ${response.body()!!.near_earth_objects}")


//        for (asteroid in response.body()!!.near_earth_objects.date) {
//            list.add(asteroid.createAsteroidModel())
//            Log.d("Tag_AAA", asteroid.createAsteroidModel().name)
//
//        }

        listOfAsteroids.value = list
    }


}