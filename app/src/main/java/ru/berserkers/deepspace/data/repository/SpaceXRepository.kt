package ru.berserkers.deepspace.data.repository

import retrofit2.Response
import ru.berserkers.deepspace.data.dto.spaceX.capsules.Capsule
import ru.berserkers.deepspace.data.dto.spaceX.cores.Core
import ru.berserkers.deepspace.data.dto.spaceX.dragons.Dragon
import ru.berserkers.deepspace.data.dto.spaceX.history.History
import ru.berserkers.deepspace.data.dto.spaceX.info.Info
import ru.berserkers.deepspace.data.dto.spaceX.landPads.LandPad
import ru.berserkers.deepspace.data.dto.spaceX.landingPads.LaunchPad
import ru.berserkers.deepspace.data.dto.spaceX.launches.Launch
import ru.berserkers.deepspace.data.dto.spaceX.roadster.Roadster

/*
 * @author Yana Glad
 */
interface SpaceXRepository {
    suspend fun getSpaceXLaunches(): Response<List<Launch>>
    suspend fun getDragons(): Response<ArrayList<Dragon>>
    suspend fun getCores(): Response<ArrayList<Core>>
    suspend fun getCapsules(): Response<ArrayList<Capsule>>
    suspend fun getHistory(): Response<ArrayList<History>>
    suspend fun getRoadster(): Response<Roadster>
    suspend fun getLaunchPads(): Response<ArrayList<LaunchPad>>
    suspend fun getLandPads(): Response<ArrayList<LandPad>>
    suspend fun getInfo(): Response<Info>
}
