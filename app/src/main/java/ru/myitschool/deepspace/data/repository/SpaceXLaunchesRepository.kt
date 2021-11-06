package ru.myitschool.deepspace.data.repository

import retrofit2.Response
import ru.myitschool.deepspace.data.dto.spaceX.launches.Launch

interface SpaceXLaunchesRepository {
    suspend fun getSpaceXLaunches(): Response<List<Launch>>
}