package ru.myitschool.nasa_bootcamp.data.api

import retrofit2.Response
import retrofit2.http.GET
import ru.myitschool.nasa_bootcamp.data.dto.upcoming.UpcomingLaunch

/*
 * @author Yana Glad
 */
interface UpcomingEventsApi {

    @GET("launches")
    suspend fun getUpcomingLaunches(): Response<List<UpcomingLaunch>>
}