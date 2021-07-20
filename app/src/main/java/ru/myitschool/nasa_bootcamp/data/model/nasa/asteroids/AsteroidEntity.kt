package ru.myitschool.nasa_bootcamp.data.model.nasa.asteroids

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "asteroid_table3")
@Parcelize
data class AsteroidEntity(@PrimaryKey(autoGenerate = true) val id: Long, val codename: String, val closeApproachDate: String,
                          val absoluteMagnitude: Double, val estimatedDiameter: Double,
                          val relativeVelocity: Double, val distanceFromEarth: Double,
                          val isPotentiallyHazardous: Boolean) : Parcelable