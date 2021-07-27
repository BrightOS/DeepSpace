package ru.myitschool.nasa_bootcamp.data.repository;

import retrofit2.Response
import javax.inject.Inject;
import ru.myitschool.nasa_bootcamp.data.api.NasaApi;
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.Asteroid
import ru.myitschool.nasa_bootcamp.utils.API_KEY

class NasaRepositoryImpl @Inject constructor(
        private val nasaApi:NasaApi
): NasaRepository {
    override suspend fun getImageOfTheDay() = nasaApi.getAstronomyImageOfTheDay()
    override suspend fun getAsteroids(beginDate: String, endDate: String): Response<Asteroid> {
        return nasaApi.getAsteroidInfo(endDate, beginDate, API_KEY)
    }

}
