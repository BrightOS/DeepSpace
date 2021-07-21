package ru.myitschool.nasa_bootcamp.data;

import javax.inject.Inject;
import ru.myitschool.nasa_bootcamp.data.api.NasaApi;
import ru.myitschool.nasa_bootcamp.data.model.ImageOfTheDayModel

class MainRepositoryImpl @Inject constructor(
        private val nasaApi:NasaApi
): MainRepository {
    override suspend fun getImageOfTheDay() = nasaApi.getAstronomyImageOfTheDay2()
}
