package ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun parseAsteroidsFromJson(jsonResult: JSONObject): ArrayList<Asteroid> {
    val near_earth_objects = jsonResult.getJSONObject("near_earth_objects")
    val asteroidList = ArrayList<Asteroid>()

    for (formattedDate in formatWeek()) {
        val dateAsteroidJsonArray = near_earth_objects.getJSONArray(formattedDate)

        for (i in 0 until dateAsteroidJsonArray.length()) {
            val asteroidJson = dateAsteroidJsonArray.getJSONObject(i)
            val close_approach = asteroidJson.getJSONArray("close_approach_data").getJSONObject(0)

            val asteroid = Asteroid(asteroidJson.getLong("id"),
                asteroidJson.getString("name"),
                formattedDate,
                asteroidJson.getDouble("absolute_magnitude_h"),
                asteroidJson.getJSONObject("estimated_diameter").getJSONObject("kilometers").getDouble("estimated_diameter_max"),
                close_approach.getJSONObject("relative_velocity").getDouble("kilometers_per_second"),
                close_approach.getJSONObject("miss_distance").getDouble("astronomical"),
                asteroidJson.getBoolean("is_potentially_hazardous_asteroid"))
            asteroidList.add(asteroid)
        }
    }

    return asteroidList
}

private fun formatWeek(): ArrayList<String> {
    val formattedDateList = ArrayList<String>()

    val calendar = Calendar.getInstance()
    for (i in 0..7) {
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        formattedDateList.add(dateFormat.format(currentTime))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    return formattedDateList
}

fun formatToday(): String {
    val currentTime = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(currentTime)
}

fun upcomingWeekFormatted(): String {
    val calendar =  Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 7)
    val currentTime = calendar.time
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(currentTime)
}