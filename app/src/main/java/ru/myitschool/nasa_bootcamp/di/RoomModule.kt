package ru.myitschool.nasa_bootcamp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
//import ru.myitschool.nasa_bootcamp.data.db.AsteroidDao
//import ru.myitschool.nasa_bootcamp.data.db.AsteroidDatabase
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object RoomModule {
//
//
//    @Provides
//    @Singleton
//    fun getDatabase(@ApplicationContext context: Context): AsteroidDatabase {
//        return Room.databaseBuilder(
//            context,
//            AsteroidDatabase::class.java,
//            "asteroids26"
//        ).build()
//    }
//
//    @Provides
//    @Singleton
//    fun getAsteroidDao(database: AsteroidDatabase): AsteroidDao {
//        return database.asteroidDao
//    }
//
//}