package ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids

import android.util.Log
import com.google.gson.JsonObject
import ru.myitschool.nasa_bootcamp.data.model.AsteroidModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/*
 * @author Yana Glad
 */
fun parseAsteroidsJsonResult(jsonResult: JsonObject): ArrayList<AsteroidModel> {

    val asteroidList = ArrayList<AsteroidModel>()
    val nextSevenDaysFormattedDates = getNextSevenDays()

    for (formattedDate in nextSevenDaysFormattedDates) {
        Log.d("Tag_parse", formattedDate)

        val dateAsteroidJsonArray = jsonResult.get(formattedDate)?.asJsonArray

        if (dateAsteroidJsonArray != null)
            for (i in 0 until dateAsteroidJsonArray.size()) {
                val asteroidJson: JsonObject = dateAsteroidJsonArray.get(i).asJsonObject


                val id = asteroidJson.get("id").asLong
                val name = asteroidJson.get("name").asString
                val absoluteMagnitude = asteroidJson.get("absolute_magnitude_h").asDouble

                val meters = asteroidJson.get("estimated_diameter").asJsonObject
                    .get("meters").asJsonObject

                val diameterMin = meters.get("estimated_diameter_min").asDouble
                val diameterMax = meters.get("estimated_diameter_max").asDouble

                val estimatedDiameter =
                    if (diameterMax < 1000)
                        "%.2f - %.2f meters".format(diameterMin, diameterMax)
                    else
                        "%.2f - %.2f kilometers".format(diameterMin / 1000, diameterMax / 1000)

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
                    formattedDate,
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
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        dateList.add(dateFormat.format(currentTime))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    return dateList
}

fun getTodayDateFormatted(): String {
    val calendar = Calendar.getInstance()
    val currentTime = calendar.time
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return dateFormat.format(currentTime)
}

fun getTimeFormatted(time: Long) : String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return dateFormat.format(time)
}

fun getPlusNDaysTimeFormatted(time: Long, n: Int) : String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time
    val currentTime = calendar.time
    calendar.add(Calendar.DAY_OF_YEAR, n - 1)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return dateFormat.format(currentTime)
}

fun getPlusNDaysTimeFormatted(time: String, n: Int) : String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val calendar = Calendar.getInstance()
    calendar.time = dateFormat.parse(time)
    val currentTime = calendar.time
    calendar.add(Calendar.DAY_OF_YEAR, n - 1)
    return dateFormat.format(currentTime)
}

fun getPlusSevenDaysDateFormatted(): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    val currentTime = calendar.time
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return dateFormat.format(currentTime)
}
