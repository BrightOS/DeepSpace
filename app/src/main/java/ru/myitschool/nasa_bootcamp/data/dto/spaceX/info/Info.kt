package ru.myitschool.nasa_bootcamp.data.dto.spaceX.info

import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.data.model.InfoModel

data class Info(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("founder") val founder: String,
    @field:SerializedName("founded") val founded: Int,
    @field:SerializedName("employees") val employees: Int,
    @field:SerializedName("vehicles") val vehicles: Int,
    @field:SerializedName("launch_sites") val launch_sites: Int,
    @field:SerializedName("ceo") val ceo: String,
    @field:SerializedName("cto") val cto: String,
    @field:SerializedName("coo") val coo: String,
    @field:SerializedName("cto_propulsion") val cto_propulsion: String,
    @field:SerializedName("valuation") val valuation: Long,
    @field:SerializedName("headquarters") val headquarters: Headquarters,
    @field:SerializedName("summary") val summary: String
) {
    fun createInfoModel(): InfoModel {
        return InfoModel(
            name,
            founder,
            founded,
            employees,
            vehicles,
            launch_sites,
            ceo,
            cto,
            coo,
            cto_propulsion,
            valuation,
            headquarters,
            summary
        )
    }
}

data class Headquarters(
    @field:SerializedName("address") val address: String,
    @field:SerializedName("city") val city: String,
    @field:SerializedName("state") val state: String
)
//{
//    "name": "SpaceX",
//    "founder": "Elon Musk",
//    "founded": 2002,
//    "employees": 7000,
//    "vehicles": 3,
//    "launch_sites": 3,
//    "test_sites": 1,
//    "ceo": "Elon Musk",
//    "cto": "Elon Musk",
//    "coo": "Gwynne Shotwell",
//    "cto_propulsion": "Tom Mueller",
//    "valuation": 15000000000,
//    "headquarters": {
//    "address": "Rocket Road",
//    "city": "Hawthorne",
//    "state": "California"
//},
//    "summary": "SpaceX designs, manufactures and launches advanced rockets and spacecraft. The company was founded in 2002 to revolutionize space technology, with the ultimate goal of enabling people to live on other planets."
//}