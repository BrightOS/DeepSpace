package ru.berserkers.deepspace.data.dto.spaceX.launches

import com.google.gson.annotations.SerializedName

data class Fairing(
    @field:SerializedName("reused") val reused: Boolean? = null,
    @field:SerializedName("recovery_attempt") val recovery_attempt: Boolean,
    @field:SerializedName("recovered") val recovered: Boolean,
    //@field:SerializedName("ship") val ship: String,
)
