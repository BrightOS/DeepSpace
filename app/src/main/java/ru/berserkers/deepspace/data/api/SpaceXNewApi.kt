//package ru.berserkers.deepspace.data.api
//
//import retrofit2.Response
//import retrofit2.http.GET
//import ru.berserkers.deepspace.data.dto.spaceX.capsules.Capsule
//import ru.berserkers.deepspace.data.dto.spaceX.cores.Core
//import ru.berserkers.deepspace.data.dto.spaceX.dragons.Dragon
//import ru.berserkers.deepspace.data.dto.spaceX.history.History
//import ru.berserkers.deepspace.data.dto.spaceX.info.Info
//import ru.berserkers.deepspace.data.dto.spaceX.landPads.LandPad
//import ru.berserkers.deepspace.data.dto.spaceX.landingPads.LaunchPad
//import ru.berserkers.deepspace.data.dto.spaceX.launches.Launch
//import ru.berserkers.deepspace.data.dto.spaceX.roadster.Roadster
//
///*
//* @author Yana Glad
// */
//interface SpaceXNewApi {
//
//    @GET("launches")
//    suspend fun getLaunches(): Response<List<Launch>>
//
//    @GET("history")
//    suspend fun getHistory(): Response<ArrayList<History>>
//
//    @GET("dragons")
//    suspend fun getDragons(): Response<ArrayList<Dragon>>
//
//    @GET("info")
//    suspend fun getInfo(): Response<Info>
//
//    @GET("landpads")
//    suspend fun getLandPads(): Response<ArrayList<LandPad>>
//
//    @GET("launchpads")
//    suspend fun getLaunchPads(): Response<ArrayList<LaunchPad>>
//
//    @GET("cores")
//    suspend fun getCores(): Response<ArrayList<Core>>
//
//    @GET("capsules")
//    suspend fun getCapsules(): Response<ArrayList<Capsule>>
//
//    @GET("roadster")
//    suspend fun getRoadster(): Response<Roadster>
//}
