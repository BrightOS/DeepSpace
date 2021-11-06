package ru.myitschool.deepspace.data.dto.spaceX.landPads

import com.google.gson.annotations.SerializedName
import ru.myitschool.deepspace.data.model.LandPadModel

data class LandPad(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("full_name") val full_name: String,
    @field:SerializedName("status") val status: String,
    @field:SerializedName("location") val location: LocationPad,
    @field:SerializedName("landing_type") val landing_type: String,
    @field:SerializedName("attempted_landings") val attempted_landings: Int,
    @field:SerializedName("successful_landings") val successful_landings: Int,
    @field:SerializedName("wikipedia") val wikipedia: String,
    @field:SerializedName("details") val details: String
) {
    fun createLandPadModel(): LandPadModel {
        return LandPadModel(
            id,
            full_name,
            status,
            location,
            landing_type,
            attempted_landings,
            successful_landings,
            wikipedia,
            details
        )
    }
}

data class LocationPad(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("region") val region: String,
    @field:SerializedName("latitude") val latitude: Double,
    @field:SerializedName("longitude") val longitude: Double,

    )

//{
//    "id": "LZ-1",
//    "full_name": "Landing Zone 1",
//    "status": "active",
//    "location": {
//    "name": "Cape Canaveral",
//    "region": "Florida",
//    "latitude": 28.485833,
//    "longitude": -80.544444
//},
//    "landing_type": "RTLS",
//    "attempted_landings": 10,
//    "successful_landings": 10,
//    "wikipedia": "https://en.wikipedia.org/wiki/Landing_Zones_1_and_2",
//    "details": "SpaceX's first east coast landing pad is Landing Zone 1, where the historic first Falcon 9 landing occurred in December 2015. LC-13 was originally used as a launch pad for early Atlas missiles and rockets from Lockheed Martin. LC-1 was later expanded to include Landing Zone 2 for side booster RTLS Falcon Heavy missions, and it was first used in February 2018 for that purpose."
//}
