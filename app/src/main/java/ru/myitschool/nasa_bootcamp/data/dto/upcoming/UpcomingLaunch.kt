package ru.myitschool.nasa_bootcamp.data.dto.upcoming

import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.Links
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.Rocket
import ru.myitschool.nasa_bootcamp.data.model.UpcomingLaunchModel

class UpcomingLaunch( //MAIN !!! in V5
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("patch") val patch: PatchUpcoming?,
    @field:SerializedName("date_unix") val date_unix: Long?,
    @field:SerializedName("upcoming") val upcoming: Boolean?
){
    fun createUpcomingLaunchModel() : UpcomingLaunchModel{
        return UpcomingLaunchModel(name, patch, date_unix, upcoming)
    }
}

data class PatchUpcoming(
    @field:SerializedName("small") val small: String,
    @field:SerializedName("large") val large: String
)

//{
//    "fairings": {
//    "reused": false,
//    "recovery_attempt": false,
//    "recovered": false,
//    "ships": []
//},
//    "links": {
//    "patch": {
//        "small": "https://images2.imgbox.com/3c/0e/T8iJcSN3_o.png",
//        "large": "https://images2.imgbox.com/40/e3/GypSkayF_o.png"
//    },
//    "reddit": {
//        "campaign": null,
//        "launch": null,
//        "media": null,
//        "recovery": null
//    },
//    "flickr": {
//        "small": [],
//        "original": []
//    },
//    "presskit": null,
//    "webcast": "https://www.youtube.com/watch?v=0a_00nJ_Y88",
//    "youtube_id": "0a_00nJ_Y88",
//    "article": "https://www.space.com/2196-spacex-inaugural-falcon-1-rocket-lost-launch.html",
//    "wikipedia": "https://en.wikipedia.org/wiki/DemoSat"
//},
//    "static_fire_date_utc": "2006-03-17T00:00:00.000Z",
//    "static_fire_date_unix": 1142553600,
//    "tbd": false,
//    "net": false,
//    "window": 0,
//    "rocket": "5e9d0d95eda69955f709d1eb",
//    "success": false,
//    "details": "Engine failure at 33 seconds and loss of vehicle",
//    "ships": [],
//    "capsules": [],
//    "payloads": [
//    "5eb0e4b5b6c3bb0006eeb1e1"
//    ],
//    "launchpad": "5e9e4502f5090995de566f86",
//    "auto_update": true,
//    "launch_library_id": null,
//    "failures": [
//    {
//        "time": 33,
//        "altitude": null,
//        "reason": "merlin engine failure"
//    }
//    ],
//    "crew": [],
//    "flight_number": 1,
//    "name": "FalconSat",
//    "date_utc": "2006-03-24T22:30:00.000Z",
//    "date_unix": 1143239400,
//    "date_local": "2006-03-25T10:30:00+12:00",
//    "date_precision": "hour",
//    "upcoming": false,
//    "cores": [
//    {
//        "core": "5e9e289df35918033d3b2623",
//        "flight": 1,
//        "gridfins": false,
//        "legs": false,
//        "reused": false,
//        "landing_attempt": false,
//        "landing_success": null,
//        "landing_type": null,
//        "landpad": null
//    }
//    ],
//    "id": "5eb87cd9ffd86e000604b32a"
//}