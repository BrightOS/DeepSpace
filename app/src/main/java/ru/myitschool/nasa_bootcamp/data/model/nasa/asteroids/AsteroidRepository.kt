package ru.myitschool.nasa_bootcamp.data.model.nasa.asteroids

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.kotlintraining.api.Api
import com.example.kotlintraining.api.Instance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDate

class AsteroidRepository(private val database: AsteroidDatabase) {

    @RequiresApi(Build.VERSION_CODES.O)
    private val startDate = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    private val endDate = startDate.plusDays(7)

    suspend fun updateAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val asteroidsResult = Instance.getInstance("https://api.nasa.gov/").create(Api::class.java).getAsteroidInfo(
                    formatToday(), upcomingWeekFormatted()
                )

                val parsedAsteroids = parseAsteroidsFromJson(JSONObject(asteroidsResult))

                database.asteroidDao.insertAll(*parsedAsteroids.toDatabaseModel())

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("Tag_Asteroid_Repos", "Failure while update")
                }
                e.printStackTrace()

            }

        }
    }

    suspend fun removeAsteroids() {
        withContext(Dispatchers.IO) {
            database.asteroidDao.removeAsteroidsByDate(formatToday())
        }
    }

}