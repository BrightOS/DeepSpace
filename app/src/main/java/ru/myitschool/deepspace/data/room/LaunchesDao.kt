package ru.myitschool.deepspace.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.myitschool.deepspace.data.dto.spaceX.launches.Launch

@Dao
interface LaunchesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLaunches(launches: List<Launch>)

    @Query("SELECT * FROM launches_table")
    suspend fun getAllLaunches(): List<Launch>
}