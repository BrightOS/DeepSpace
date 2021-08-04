package ru.myitschool.nasa_bootcamp

import android.app.Application
import android.hardware.SensorManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.HiltAndroidApp
import ru.myitschool.nasa_bootcamp.lookbeyond.pointing.AbstractPointing
import ru.myitschool.nasa_bootcamp.lookbeyond.pointing.VectorPointing

@HiltAndroidApp
class App: Application(){


    private lateinit var sensorManager: SensorManager

    private var model: AbstractPointing = VectorPointing()
        get() = field

    override fun onCreate() {
        super.onCreate()
        sensorManager = ContextCompat.getSystemService(this, SensorManager::class.java)!!
        model = VectorPointing()

    }
}