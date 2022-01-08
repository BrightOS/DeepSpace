package ru.berserkers.deepspace.data.api

import retrofit2.Response
import retrofit2.http.GET
import ru.berserkers.deepspace.data.dto.upcoming.UpcomingLaunch

/*
 * @author Yana Glad
 */
interface UpcomingEventsApi {

    @GET("launches")
    suspend fun getUpcomingLaunches(): Response<List<UpcomingLaunch>>
}
