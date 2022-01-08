package ru.myitschool.deepspace.navigator.managers

import android.content.Context
import android.hardware.SensorManager
import androidx.core.content.ContextCompat
import ru.myitschool.deepspace.navigator.pointing.VectorPointing
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
        sensorController = SensorManager(
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
