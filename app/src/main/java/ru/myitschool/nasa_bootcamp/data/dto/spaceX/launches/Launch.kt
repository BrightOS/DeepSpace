package ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel


@Entity(tableName = "launches_table")
class Launch( //MAIN

    @PrimaryKey(autoGenerate = false)
    @field:SerializedName("flight_number")
    val flight_number: Int,

    @field:SerializedName("mission_name")
    val mission_name: String,

    @field:SerializedName("launch_year")
    val launch_year: Int,

    @field:SerializedName("launch_date_utc")
    val launch_date_utc: String,

    @Embedded
    @field:SerializedName("links")
    val links: Links?, //Cсылка на эмблему миссии

    @Ignore
    @field:SerializedName("rocket")
    val rocket: Rocket,

    @field:SerializedName("upcoming")
    val upcoming: Boolean,

    @field:SerializedName("launch_success")
    val launch_success: Boolean,

    @field:SerializedName("launch_date_unix")
    val launch_date_unix : Int,

    @field:SerializedName("details")
    val details: String?,

    @Embedded
    @field:SerializedName("launch_site")
    val launch_site: LaunchSite

) {
    public fun createLaunchModel(): SxLaunchModel {
        return SxLaunchModel(
            launch_date_unix,
            flight_number,
            mission_name,
            launch_year,
            launch_date_utc,
            links,
            rocket,
            upcoming,
            launch_success,
            details,
            launch_site
        )
    }
    constructor(flight_number: Int,
                mission_name: String,
                launch_year: Int,
                launch_date_utc: String?,
                links: Links?, //Cсылка на эмблему миссии
                upcoming: Boolean,
                launch_success: Boolean,
                launch_date_unix : Int,
                details: String?,
                launch_site: LaunchSite)
            : this(flight_number,
        mission_name,
        launch_year,
        launch_date_utc ?: "",
        links,
        Rocket("","", FirstStage(arrayListOf(StageCore("",0,false,false))), SecondStage(0, arrayListOf(Payloads("",false,"","",0.0,"",""))), Fairing(false,false,false)),
        upcoming,
        launch_success,
        launch_date_unix,
        details,
        launch_site)
}
// Rocket("","", FirstStage(arrayListOf(StageCore("",0,false,false))), SecondStage(0, arrayListOf(Payloads("",false,"","",0.0,"",""))),