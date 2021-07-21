package com.example.kotlintraining.api

import ru.myitschool.nasa_bootcamp.data.model.spaceX.launches.Launch
import com.example.firstkotlinapp.model.rovers.Rovers
import com.example.firstkotlinapp.model.apod.Apod
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.myitschool.nasa_bootcamp.data.model.spaceX.cores.Core
import ru.myitschool.nasa_bootcamp.data.model.spaceX.dragons.Dragon
import ru.myitschool.nasa_bootcamp.data.model.spaceX.history.History
import ru.myitschool.nasa_bootcamp.data.model.spaceX.info.Info
import ru.myitschool.nasa_bootcamp.data.model.spaceX.landingPads.LandingPad
import ru.myitschool.nasa_bootcamp.data.model.spaceX.roadster.Roadster

interface Api {


    @GET("launches")
    suspend fun getLaunches(): Response<ArrayList<Launch>>//https://api.spacexdata.com/v4/

    @GET("history")
    suspend fun getHistory(): Response<ArrayList<History>>//https://api.spacexdata.com/v4/

    @GET("dragons")
    suspend fun getDragons(): Response<ArrayList<Dragon>>//https://api.spacexdata.com/v4/

    @GET("info")
    suspend fun getInfo(): Response<Info>//https://api.spacexdata.com/v4/

    @GET("landpads")
    suspend fun getLandingPads(): Response<ArrayList<LandingPad>>//https://api.spacexdata.com/v4/

    @GET("cores")
    suspend fun getCores(): Response<ArrayList<Core>>//https://api.spacexdata.com/v4/

    @GET("cores")
    suspend fun getRoadster(): Response<Roadster>//https://api.spacexdata.com/v4/

//
//    @GET("ships")
//    suspend fun getShips(): Response<ArrayList<Ship>>//https://api.spacexdata.com/v4/
}