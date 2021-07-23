package ru.myitschool.nasa_bootcamp.data.repository

import retrofit2.Response
import ru.myitschool.nasa_bootcamp.data.api.NasaApi
import ru.myitschool.nasa_bootcamp.data.api.SpaceXApi
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.Launch
import javax.inject.Inject

class SpaceXLaunchRepositoryImpl @Inject constructor(
    private val spaceXApi: SpaceXApi
) : SpaceXLaunchRepository {
    override suspend fun getSpaceXLaunches()  = spaceXApi.getLaunches()
}
