package ru.myitschool.nasa_bootcamp.data.repository

import retrofit2.Response
import ru.myitschool.nasa_bootcamp.data.api.NasaApi
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.Asteroid
import ru.myitschool.nasa_bootcamp.utils.API_KEY
import javax.inject.Inject

class AsteroidRepository2Impl @Inject constructor(
    private val nasaApi: NasaApi,
) : AsteroidRepository2 {

    override suspend fun getAsteroids(beginDate: String, endDate: String): Response<Asteroid> {
       return nasaApi.getAsteroidInfo(endDate, beginDate, API_KEY)
    }
}
//"2015-09-07","2015-09-08"