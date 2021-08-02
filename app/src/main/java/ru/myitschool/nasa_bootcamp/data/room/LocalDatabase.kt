package ru.myitschool.nasa_bootcamp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel

@Database(entities = [SxLaunchModel::class],version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase(){

    abstract fun getLaunchesDao(): LaunchesDao
}