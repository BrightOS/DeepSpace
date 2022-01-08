package ru.berserkers.deepspace.data.repository

import retrofit2.Response
import ru.berserkers.deepspace.data.dto.upcoming.UpcomingLaunch

/*
 * @author Yana Glad
 */
interface UpcomingRepository {
    suspend fun getUpcomingLaunches(): Response<List<UpcomingLaunch>>
}
