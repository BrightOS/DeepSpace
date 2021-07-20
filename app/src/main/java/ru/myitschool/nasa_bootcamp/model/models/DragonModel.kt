package ru.myitschool.nasa_bootcamp.model.models

import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.model.spaceX.dragons.PayloadMass
import ru.myitschool.nasa_bootcamp.model.spaceX.dragons.Thruster

data class DragonModel(
    val id: String,
    val name: String,
    val type: String,
    val active: Boolean,
    val orbit_duration_yr: Int,
    val dry_mass_kg: Int,
    val first_flight: String,
    val thrusters: List<Thruster>,
    val launch_payload_mass: PayloadMass,
    val return_payload_mass: PayloadMass,
    val description: String
)