package ru.berserkers.deepspace.data.repository

import retrofit2.Response
import ru.berserkers.deepspace.data.api.LaunchApi
import ru.berserkers.deepspace.data.dto.spaceX.launches.Launch
import javax.inject.Inject

/*
 * @author Yana Glad
 */
class SpaceXLaunchesRepositoryImpl @Inject constructor(
    private val launchesApi: LaunchApi,
) : SpaceXLaunchesRepository {
    override suspend fun getSpaceXLaunches(): Response<List<Launch>> = launchesApi.getLaunches()
}
