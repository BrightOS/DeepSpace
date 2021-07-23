package ru.myitschool.nasa_bootcamp.data.api

import retrofit2.Response
import retrofit2.http.GET
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.cores.Core
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.dragons.Dragon
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.history.History
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.info.Info
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.landingPads.LandingPad
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.Launch
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.roadster.Roadster

interface SpaceXApi {

    @GET("launches")
    suspend fun getLaunches(): Response<List<Launch>>

    @GET("history")
    suspend fun getHistory(): Response<ArrayList<History>>

    @GET("dragons")
    suspend fun getDragons(): Response<ArrayList<Dragon>>

    @GET("info")
    suspend fun getInfo(): Response<Info>

    @GET("landpads")
    suspend fun getLandingPads(): Response<ArrayList<LandingPad>>

    @GET("cores")
    suspend fun getCores(): Response<ArrayList<Core>>

    @GET("cores")
    suspend fun getRoadster(): Response<Roadster>

//
//    @GET("ships")
//    suspend fun getShips(): Response<ArrayList<Ship>>
}