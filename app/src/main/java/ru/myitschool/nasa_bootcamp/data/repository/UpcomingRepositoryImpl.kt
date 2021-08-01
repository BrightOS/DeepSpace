package ru.myitschool.nasa_bootcamp.data.repository

import retrofit2.Response
import ru.myitschool.nasa_bootcamp.data.api.UpcomingEventsApi
import ru.myitschool.nasa_bootcamp.data.dto.upcoming.UpcomingLaunch
import javax.inject.Inject

class UpcomingRepositoryImpl @Inject constructor(
    private val upcomingEventsApi: UpcomingEventsApi
) : UpcomingRepository {
    override suspend fun getUpcomingLaunches(): Response<List<UpcomingLaunch>> =
        upcomingEventsApi.getUpcomingLaunches()
}