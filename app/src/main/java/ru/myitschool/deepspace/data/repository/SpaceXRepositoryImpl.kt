package ru.myitschool.deepspace.data.repository

import retrofit2.Response
import ru.myitschool.deepspace.data.api.SpaceXApi
import ru.myitschool.deepspace.data.dto.spaceX.capsules.Capsule
import ru.myitschool.deepspace.data.dto.spaceX.cores.Core
import ru.myitschool.deepspace.data.dto.spaceX.dragons.Dragon
import ru.myitschool.deepspace.data.dto.spaceX.history.History
import ru.myitschool.deepspace.data.dto.spaceX.info.Info
import ru.myitschool.deepspace.data.dto.spaceX.landPads.LandPad
import ru.myitschool.deepspace.data.dto.spaceX.landingPads.LaunchPad
import ru.myitschool.deepspace.data.dto.spaceX.roadster.Roadster
import javax.inject.Inject

class SpaceXRepositoryImpl @Inject constructor(
    private val spaceXApi: SpaceXApi
) : SpaceXRepository {
    override suspend fun getSpaceXLaunches()  = spaceXApi.getLaunches()
    override suspend fun getDragons(): Response<ArrayList<Dragon>> = spaceXApi.getDragons()
    override suspend fun getCores(): Response<ArrayList<Core>> = spaceXApi.getCores()
    override suspend fun getCapsules(): Response<ArrayList<Capsule>> = spaceXApi.getCapsules()
    override suspend fun getHistory(): Response<ArrayList<History>>  = spaceXApi.getHistory()
    override suspend fun getRoadster(): Response<Roadster> = spaceXApi.getRoadster()
    override suspend fun getLaunchPads(): Response<ArrayList<LaunchPad>> = spaceXApi.getLaunchPads()
    override suspend fun getLandPads(): Response<ArrayList<LandPad>> = spaceXApi.getLandPads()
    override suspend fun getInfo(): Response<Info> = spaceXApi.getInfo()
}
