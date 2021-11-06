package ru.myitschool.deepspace.data.dto.spaceX.launches

import com.google.gson.annotations.SerializedName

class FirstStage (
     @field:SerializedName("cores") val cores: ArrayList<StageCore>)

data class StageCore(
    @field:SerializedName("core_serial") val core_serial: String,
    @field:SerializedName("flight") val flight: Int,
    @field:SerializedName("legs") val legs: Boolean,
    @field:SerializedName("reused") val reused: Boolean

)

//"core_serial": "Merlin1A",
//"flight": 1,
//"block": null,
//"gridfins": false,
//"legs": false,
//"reused": false,
//"land_success": null,
//"landing_intent": false,
//"landing_type": null,
//"landing_vehicle": null
