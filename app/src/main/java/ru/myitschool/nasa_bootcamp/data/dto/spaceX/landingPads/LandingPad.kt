package ru.myitschool.nasa_bootcamp.data.dto.spaceX.landingPads

import com.google.gson.annotations.SerializedName

data class LandingPad (
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("status") val status: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("location") val location: LocationPadLaunch
    )

data class LocationPadLaunch(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("region") val region: String,
    @field:SerializedName("latitude") val latitude: Double,
    @field:SerializedName("longitude") val longitude: Double,
    @field:SerializedName("vehicles_launched") val vehicles_launched: ArrayList<String>,
    @field:SerializedName("attempted_launches") val attempted_launches: Int,
    @field:SerializedName("successful_launches") val successful_launches: Int,
    @field:SerializedName("wikipedia") val wikipedia: String,
    @field:SerializedName("details") val details: String,
    @field:SerializedName("site_name_long") val site_name_long: String,
    )

//{
//    "id": 6,
//    "status": "active",
//    "location": {
//    "name": "Vandenberg Air Force Base",
//    "region": "California",
//    "latitude": 34.632093,
//    "longitude": -120.610829
//},
//    "vehicles_launched": [
//    "Falcon 9"
//    ],
//    "attempted_launches": 12,
//    "successful_launches": 12,
//    "wikipedia": "https://en.wikipedia.org/wiki/Vandenberg_AFB_Space_Launch_Complex_4",
//    "details": "SpaceX primary west coast launch pad for polar orbits and sun synchronous orbits, primarily used for Iridium. Also intended to be capable of launching Falcon Heavy.",
//    "site_id": "vafb_slc_4e",
//    "site_name_long": "Vandenberg Air Force Base Space Launch Complex 4E"
//},