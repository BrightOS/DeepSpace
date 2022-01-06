package ru.myitschool.deepspace.data.repository;

import ru.myitschool.deepspace.data.dto.nasa.rovers.Rovers
import retrofit2.Response
import javax.inject.Inject;
import ru.myitschool.deepspace.data.api.NasaApi;
import ru.myitschool.deepspace.data.dto.nasa.asteroids.Asteroid
import ru.myitschool.deepspace.utils.API_KEY

class NasaRepositoryImpl @Inject constructor(
        private val nasaApi:NasaApi
): NasaRepository {
    override suspend fun getImageOfTheDay() = nasaApi.getAstronomyImageOfTheDay()
    override suspend fun getAsteroids(beginDate: String, endDate: String): Response<Asteroid> {
        return nasaApi.getAsteroidInfo(endDate, beginDate, API_KEY)
    }
    override suspend fun getRoverCuriosityPhotos(sol: Int): Response<Rovers> = nasaApi.getRoverCuriosityPhotos(
        API_KEY, sol)
    override suspend fun getRoverOpportunityPhotos(sol: Int): Response<Rovers> = nasaApi.getRoverOpportunityPhotos(API_KEY, sol)
    override suspend fun getRoverSpiritPhotos(sol: Int): Response<Rovers> = nasaApi.getRoverSpiritPhotos(API_KEY, sol)
}
