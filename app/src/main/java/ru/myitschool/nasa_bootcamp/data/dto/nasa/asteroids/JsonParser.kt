package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import android.util.Log
import com.google.gson.JsonObject
import ru.myitschool.nasa_bootcamp.data.model.AsteroidModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun parseAsteroidsJsonResult(jsonResult: JsonObject): ArrayList<AsteroidModel> {

    val asteroidList = ArrayList<AsteroidModel>()
    val nextSevenDaysFormattedDates = getNextSevenDays()

    for (formattedDate in nextSevenDaysFormattedDates) {
        Log.d("Tag_parse", formattedDate)

        val dateAsteroidJsonArray = jsonResult.get(formattedDate).asJsonArray

        for (i in 0 until dateAsteroidJsonArray.size()) {
            val asteroidJson: JsonObject = dateAsteroidJsonArray.get(i).asJsonObject


            val id = asteroidJson.get("id").asLong
            val name = asteroidJson.get("name").asString
            val absoluteMagnitude = asteroidJson.get("absolute_magnitude_h").asDouble

            val estimatedDiameter = asteroidJson.get("estimated_diameter").asJsonObject
                .get("kilometers").asJsonObject.get("estimated_diameter_max").asDouble

            val closeApproachData = asteroidJson
                .get("close_approach_data").asJsonArray[0].asJsonObject

            val relativeVelocity = closeApproachData.get("relative_velocity").asJsonObject
                .get("kilometers_per_second").asDouble

            val distanceFromEarth = closeApproachData.get("miss_distance").asJsonObject
                .get("astronomical").asDouble

            val danger = asteroidJson.get("is_potentially_hazardous_asteroid").asBoolean

            val asteroid = AsteroidModel(
                id,
                name,
                absoluteMagnitude,
                estimatedDiameter,
                relativeVelocity,
                distanceFromEarth,
                danger
            )
            asteroidList.add(asteroid)
        }
    }

    return asteroidList
}

private fun getNextSevenDays(): ArrayList<String> {
    val dateList = ArrayList<String>()

    val calendar = Calendar.getInstance()
    for (i in 0..7) {
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateList.add(dateFormat.format(currentTime))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    return dateList
}

fun getTodayDateFormatted(): String {
    val calendar = Calendar.getInstance()
    val currentTime = calendar.time
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(currentTime)
}

fun getPlusSevenDaysDateFormatted(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    val currentTime = calendar.time
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(currentTime)
}