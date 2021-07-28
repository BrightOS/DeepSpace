package ru.myitschool.nasa_bootcamp.data.dto.spaceX.launches

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

//"second_stage": {
//    "block": 1,
//    "payloads": [
//    {
//        "payload_id": "FalconSAT-2",
//        "norad_id": [],
//        "reused": false,
//        "customers": [
//        "DARPA"
//        ],
//        "nationality": "United States",
//        "manufacturer": "SSTL",
//        "payload_type": "Satellite",
//
//        "payload_mass_lbs": 43,
//        "orbit": "LEO",
//        "orbit_params": {
//        "reference_system": "geocentric",
//        "regime": "low-earth",
//        "longitude": null,
//        "semi_major_axis_km": null,
//        "eccentricity": null,
//        "periapsis_km": 400,
//        "apoapsis_km": 500,
//        "inclination_deg": 39,
//        "period_min": null,
//        "lifespan_years": null,
//        "epoch": null,
//        "mean_motion": null,
//        "raan": null,
//        "arg_of_pericenter": null,
//        "mean_anomaly": null
//    }
//    }
//    ]
//}