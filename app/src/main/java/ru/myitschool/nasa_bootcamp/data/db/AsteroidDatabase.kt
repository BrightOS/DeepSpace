package ru.myitschool.nasa_bootcamp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.AsteroidEntity

@Database(entities = [AsteroidEntity::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}

