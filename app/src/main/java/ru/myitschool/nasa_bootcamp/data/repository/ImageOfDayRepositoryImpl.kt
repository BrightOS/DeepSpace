package ru.myitschool.nasa_bootcamp.data.repository;

import javax.inject.Inject;
import ru.myitschool.nasa_bootcamp.data.api.NasaApi;

class ImageOfDayRepositoryImpl @Inject constructor(
        private val nasaApi:NasaApi
): ImageOfDayRepository {
    override suspend fun getImageOfTheDay() = nasaApi.getAstronomyImageOfTheDay()
}
