package ru.myitschool.nasa_bootcamp.data.repository

import ru.myitschool.nasa_bootcamp.data.api.NasaApi
import ru.myitschool.nasa_bootcamp.utils.API_KEY
import javax.inject.Inject

class AsteroidRepository2Impl @Inject constructor(
    private val nasaApi: NasaApi,
    private val beginDate : String,
    private val endDate : String
): AsteroidRepository2 {
    override suspend fun getAsteroids() = nasaApi.getAsteroidInfo(endDate,beginDate, API_KEY) //?????
}
//"2015-09-07","2015-09-08"