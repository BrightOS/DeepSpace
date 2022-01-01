package ru.myitschool.deepspace.data.repository

import retrofit2.Response
import ru.myitschool.deepspace.data.api.LaunchApi
import ru.myitschool.deepspace.data.dto.spaceX.launches.Launch
import javax.inject.Inject

/*
 * @author Yana Glad
 */
class SpaceXLaunchesRepositoryImpl @Inject constructor(
    private val launchesApi: LaunchApi,
) : SpaceXLaunchesRepository {
    override suspend fun getSpaceXLaunches(): Response<List<Launch>> = launchesApi.getLaunches()
}