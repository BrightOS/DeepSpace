package ru.berserkers.deepspace.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.berserkers.deepspace.data.dto.spaceX.launches.Launch

/*
 * @author Danil Khairulin
 */
@Database(entities = [Launch::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun getLaunchesDao(): LaunchesDao
}
