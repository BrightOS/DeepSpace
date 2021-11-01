package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.getPlusSevenDaysDateFormatted
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.getTodayDateFormatted
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.parseAsteroidsJsonResult
import ru.myitschool.nasa_bootcamp.data.model.AsteroidModel
import ru.myitschool.nasa_bootcamp.data.repository.NasaRepository
import javax.inject.Inject

/*
 * @author Denis Shaikhlbarin
 */
@HiltViewModel
class AsteroidRadarViewModelImpl @Inject constructor(
    private val repository: NasaRepository
) : ViewModel(), AsteroidRadarViewModel {

    var list: ArrayList<AsteroidModel> = arrayListOf()

    private var listOfAsteroids: MutableLiveData<ArrayList<AsteroidModel>> =
        MutableLiveData<ArrayList<AsteroidModel>>()


    @DelicateCoroutinesApi
    override suspend fun getAsteroidList() {
        try {
            val response =
                repository.getAsteroids(getTodayDateFormatted(), getPlusSevenDaysDateFormatted())
            list = parseAsteroidsJsonResult(response.body()!!.near_earth_objects)

            listOfAsteroids.postValue(list)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getAsteroidListViewModel(): MutableLiveData<ArrayList<AsteroidModel>> {
        return listOfAsteroids
    }

    override fun getViewModelScope() = viewModelScope

}