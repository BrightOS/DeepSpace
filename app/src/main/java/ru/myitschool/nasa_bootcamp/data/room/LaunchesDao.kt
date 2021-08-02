package ru.myitschool.nasa_bootcamp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel

@Dao
interface LaunchesDao {

    @Insert
    suspend fun insertAllLaunches(launches: List<SxLaunchModel>)

    @Query("SELECT * FROM launches_table")
    suspend fun getAllLaunches(): List<SxLaunchModel>
}