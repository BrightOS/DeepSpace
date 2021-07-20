package ru.myitschool.nasa_bootcamp.data.model.nasa.asteroids

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Asteroid(val id: Long, val codename: String, val close_approach_date: String,
                    val absolute_magnitude: Double, val estimated_diameter: Double,
                    val relative_velocity: Double, val distance_from_earth: Double,
                    val is_dangerous: Boolean) : Parcelable