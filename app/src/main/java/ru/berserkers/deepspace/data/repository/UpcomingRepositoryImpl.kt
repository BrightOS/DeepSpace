package ru.berserkers.deepspace.data.repository

import retrofit2.Response
import ru.berserkers.deepspace.data.api.UpcomingEventsApi
import ru.berserkers.deepspace.data.dto.upcoming.UpcomingLaunch
import javax.inject.Inject

/*
 * @author Yana Glad
 */
class UpcomingRepositoryImpl @Inject constructor(
    private val upcomingEventsApi: UpcomingEventsApi,
) : UpcomingRepository {
    override suspend fun getUpcomingLaunches(): Response<List<UpcomingLaunch>> =
        upcomingEventsApi.getUpcomingLaunches()
}
