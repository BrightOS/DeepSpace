package ru.myitschool.nasa_bootcamp.data

import ru.myitschool.nasa_bootcamp.data.api.NasaApi
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val nasaApi: NasaApi
) {
    suspend fun getImageOfDay() = nasaApi.getAstronomyImageOfTheDay2()
}
