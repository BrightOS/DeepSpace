package ru.berserkers.deepspace.data.repository

import retrofit2.Response
import ru.berserkers.deepspace.data.dto.spaceX.launches.Launch

/*
 * @author Yana Glad
 */
interface SpaceXLaunchesRepository {
    suspend fun getSpaceXLaunches(): Response<List<Launch>>
}
