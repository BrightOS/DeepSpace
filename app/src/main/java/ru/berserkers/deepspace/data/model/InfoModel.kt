package ru.berserkers.deepspace.data.model

import ru.berserkers.deepspace.data.dto.spaceX.info.Headquarters

class InfoModel(
    val name: String,
    val founder: String,
    val founded: Int,
    val employees: Int,
    val vehicles: Int,
    val launch_sites: Int,
    val ceo: String,
    val cto: String,
    val coo: String,
    val cto_propulsion: String,
    val valuation: Long,
    val headquarters: Headquarters,
    val summary: String
)
