package ru.myitschool.nasa_bootcamp.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches.*


@Entity(tableName = "launches_table")
data class SxLaunchModel(
    val launch_date_unix: Int,
    val flight_number: Int,
    val mission_name: String,
    val launch_year: Int,
    val launch_date_utc: String,

    @Embedded
    val links: Links?, //Cсылка на эмблему миссии

    val upcoming: Boolean?,
    val launch_success: Boolean,
    val details: String?,
    @Embedded
    val launch_site: LaunchSite
){
    constructor(launch_date_unix: Int,
                flight_number: Int,
                mission_name: String,
                launch_year: Int,
                launch_date_utc: String,
                links: Links, //Cсылка на эмблему миссии
                rocket: Rocket,
                upcoming: Boolean,
                launch_success: Boolean,
                details: String?,
                launch_site: LaunchSite) : this(launch_date_unix, flight_number, mission_name, launch_year, launch_date_utc, links,upcoming, launch_success, details, launch_site){
                    this.rocket = rocket
                }

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @Ignore
    var rocket: Rocket = Rocket("","", FirstStage(arrayListOf(StageCore("",0,false,false))), SecondStage(0, arrayListOf(Payloads("",false,"","",0.0,"",""))),
        Fairing(false,false,false)
    )
}