package ru.myitschool.deepspace.navigator.control

import android.content.Context
import android.hardware.SensorManager
import androidx.core.content.ContextCompat
import ru.myitschool.deepspace.navigator.managers.AbstractManager
import ru.myitschool.deepspace.navigator.managers.GpsManager
import ru.myitschool.deepspace.navigator.managers.ZoomManager
import java.util.*


class Managers(context: Context?, gpsManager: GpsManager) : AbstractManager {

    private val controllers = ArrayList<AbstractManager>()
    private val zoomController: ZoomManager
    private val sensorController: ru.myitschool.deepspace.navigator.managers.SensorManager

    override var pointing: VectorPointing? = null
        set(value) {
            for (controller in controllers) {
                controller.pointing = value
            }
            field = pointing
        }

    override fun start() {
        for (controller in controllers) controller.start()
    }

    override fun stop() {
        for (controller in controllers) controller.stop()
    }

    private fun addController(manager: AbstractManager) {
        controllers.add(manager)
    }

    fun zoomBy(ratio: Double) {
        zoomController.zoomBy(ratio)
    }

    init {
        addController(gpsManager)
        sensorController = ru.myitschool.deepspace.navigator.managers.SensorManager(
            ContextCompat.getSystemService(
                context!!,
                SensorManager::class.java
            )!!
        )
        addController(sensorController)
        zoomController = ZoomManager()
        addController(zoomController)
    }
}