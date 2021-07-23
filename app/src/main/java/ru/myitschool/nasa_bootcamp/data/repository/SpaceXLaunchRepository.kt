package ru.myitschool.nasa_bootcamp.data.repository

import retrofit2.Response
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.Launch

interface SpaceXLaunchRepository {
    suspend fun getSpaceXLaunches(): Response<List<Launch>>
}