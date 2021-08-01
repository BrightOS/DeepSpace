package ru.myitschool.nasa_bootcamp.data.repository

import retrofit2.Response
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.Launch
import ru.myitschool.nasa_bootcamp.data.dto.upcoming.UpcomingLaunch

interface UpcomingRepository {
    suspend fun getUpcomingLaunches(): Response<List<UpcomingLaunch>>
}