package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.nasa_bootcamp.data.model.AsteroidModel
import ru.myitschool.nasa_bootcamp.data.repository.AsteroidRepository2
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class AsteroidRadarViewModelImpl @Inject constructor(
    private val repository: AsteroidRepository2
) : ViewModel(), AsteroidRadarViewModel {

    var list: ArrayList<AsteroidModel> = arrayListOf()

    var listOfAsteroids: MutableLiveData<ArrayList<AsteroidModel>> =
        MutableLiveData<ArrayList<AsteroidModel>>()


    override suspend fun getAsteroidList() {
        val response = repository.getAsteroids(getTodayDateFormatted(), getPlusSevenDaysDateFormatted())
        list = parseAsteroidsJsonResult(response.body()!!.near_earth_objects)

        listOfAsteroids.value = list
    }


}