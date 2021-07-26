package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
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
        val response = repository.getAsteroids(getTodayDateFormatted(), getPlusSevenDaysDateFormatted())
        list = parseAsteroidsJsonResult(response.body()!!.near_earth_objects)

        listOfAsteroids.value = list
    }


}