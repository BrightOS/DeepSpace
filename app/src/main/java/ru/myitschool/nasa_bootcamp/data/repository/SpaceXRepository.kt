package ru.myitschool.nasa_bootcamp.data.repository

import retrofit2.Response
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.capsules.Capsule
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.cores.Core
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.dragons.Dragon
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.history.History
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.landPads.LandPad
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.landingPads.LaunchPad
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.Launch
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.roadster.Roadster

interface SpaceXRepository {
    suspend fun getSpaceXLaunches(): Response<List<Launch>>
    suspend fun getDragons(): Response<ArrayList<Dragon>>
    suspend fun getCores(): Response<ArrayList<Core>>
    suspend fun getCapsules(): Response<ArrayList<Capsule>>
    suspend fun getHistory(): Response<ArrayList<History>>
    suspend fun getRoadster(): Response<Roadster>
    suspend fun getLaunchPads(): Response<ArrayList<LaunchPad>>
    suspend fun getLandPads(): Response<ArrayList<LandPad>>

}