package ru.myitschool.nasa_bootcamp.data.model.nasa.asteroids

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    @Query("select * from asteroid_table3 where closeApproachDate = :date")
    fun getOneDayAsteroids(date: String): LiveData<List<AsteroidEntity>>

    @Query("select * from asteroid_table3 where closeApproachDate between :startDate and :endDate order by date(closeApproachDate) asc")
    fun getWeeklyAsteroids(startDate: String, endDate: String) : LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroids: AsteroidEntity)

    @Query("delete from asteroid_table3 where closeApproachDate < :date")
    suspend fun removeAsteroidsByDate(date: String)
}