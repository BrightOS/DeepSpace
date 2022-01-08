package ru.berserkers.deepspace.data.api

import retrofit2.Response
import retrofit2.http.GET
import ru.berserkers.deepspace.data.dto.spaceX.launches.Launch

interface LaunchApi {
    @GET("launches")
    suspend fun getLaunches(): Response<List<Launch>>
}
