package ru.myitschool.nasa_bootcamp

import android.app.Application
import android.hardware.SensorManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.HiltAndroidApp
import ru.myitschool.nasa_bootcamp.lookbeyond.control.AbstractPointing
import ru.myitschool.nasa_bootcamp.lookbeyond.control.VectorPointing
import ru.myitschool.nasa_bootcamp.lookbeyond.layer.LayerManager


@HiltAndroidApp
class App: Application(){

    var layerManager: LayerManager? = null
    var sensorManager: SensorManager? = null
    var model: AbstractPointing? = null

    override fun onCreate() {
        super.onCreate()
        sensorManager = ContextCompat.getSystemService(this, SensorManager::class.java)

        model = VectorPointing()
        layerManager = LayerManager(resources, model!!)

     }
}