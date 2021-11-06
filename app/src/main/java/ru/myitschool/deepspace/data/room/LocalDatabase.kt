package ru.myitschool.deepspace.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.myitschool.deepspace.data.dto.spaceX.launches.Launch

@Database(entities = [Launch::class],version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase(){

    abstract fun getLaunchesDao(): LaunchesDao
}