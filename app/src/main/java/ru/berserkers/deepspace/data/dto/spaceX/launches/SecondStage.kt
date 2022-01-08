package ru.berserkers.deepspace.data.dto.spaceX.launches

import com.google.gson.annotations.SerializedName

data class SecondStage (
    @field:SerializedName("block") val block: Int,
    @field:SerializedName("payloads") val payloads: ArrayList<Payloads>
)
data class Payloads(
    @field:SerializedName("payload_id") val payload_id: String,
    @field:SerializedName("reused") val reused: Boolean,
    @field:SerializedName("nationality") val nationality: String,
    @field:SerializedName("manufacturer") val manufacturer: String,
    @field:SerializedName("payload_mass_kg") val payload_mass_kg: Double,
    @field:SerializedName("payload_type") val payload_type: String,
    @field:SerializedName("reference_system") val reference_system: String
    )
