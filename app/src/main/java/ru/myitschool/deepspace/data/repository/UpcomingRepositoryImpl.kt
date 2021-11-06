package ru.myitschool.deepspace.data.repository

import retrofit2.Response
import ru.myitschool.deepspace.data.api.UpcomingEventsApi
import ru.myitschool.deepspace.data.dto.upcoming.UpcomingLaunch
import javax.inject.Inject

class UpcomingRepositoryImpl @Inject constructor(
    private val upcomingEventsApi: UpcomingEventsApi
) : UpcomingRepository {
    override suspend fun getUpcomingLaunches(): Response<List<UpcomingLaunch>> =
        upcomingEventsApi.getUpcomingLaunches()
}