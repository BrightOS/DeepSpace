package ru.myitschool.deepspace

import android.app.Application
import android.hardware.SensorManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.HiltAndroidApp
import ru.myitschool.deepspace.navigator.control.AbstractPointing
import ru.myitschool.deepspace.navigator.control.VectorPointing


@HiltAndroidApp
class App: Application(){

    var sensorManager: SensorManager? = null
    var model: AbstractPointing? = null

    override fun onCreate() {
        super.onCreate()
        sensorManager = ContextCompat.getSystemService(this, SensorManager::class.java)

        model = VectorPointing()

     }
}

