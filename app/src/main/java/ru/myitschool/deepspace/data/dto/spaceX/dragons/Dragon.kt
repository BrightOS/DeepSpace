package ru.myitschool.deepspace.data.dto.spaceX.dragons

import com.google.gson.annotations.SerializedName
import ru.myitschool.deepspace.data.model.DragonModel

class Dragon(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("type") val type: String,
    @field:SerializedName("active") val active: Boolean,
    @field:SerializedName("orbit_duration_yr") val orbit_duration_yr: Int,
    @field:SerializedName("dry_mass_kg") val dry_mass_kg: Int,
    @field:SerializedName("first_flight") val first_flight: String,
    @field:SerializedName("thrusters") val thrusters: List<Thruster>,
    @field:SerializedName("launch_payload_mass") val launch_payload_mass: PayloadMass,
    @field:SerializedName("return_payload_mass") val return_payload_mass: PayloadMass,
    @field:SerializedName("description") val description: String

) {

    public fun createDragonModel(): DragonModel {
        return DragonModel(
            id,
            name,
            type,
            active,
            orbit_duration_yr,
            dry_mass_kg,
            first_flight,
            thrusters,
            launch_payload_mass,
            return_payload_mass,
            description
        )
    }
}