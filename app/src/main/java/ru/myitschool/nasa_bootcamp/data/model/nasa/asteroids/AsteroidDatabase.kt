package ru.myitschool.nasa_bootcamp.data.model.nasa.asteroids

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AsteroidEntity::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}

private var INSTANCE: AsteroidDatabase? = null


fun getInstance(context: Context): AsteroidDatabase? {
    synchronized(AsteroidDatabase::class) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidDatabase::class.java,
                "asteroids3"
            ).build()
        }
    }
    return INSTANCE
}

