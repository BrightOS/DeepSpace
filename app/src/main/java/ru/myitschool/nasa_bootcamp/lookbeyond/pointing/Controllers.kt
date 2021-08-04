package ru.myitschool.nasa_bootcamp.lookbeyond.pointing

import java.util.*
import javax.inject.Inject

interface StartStop {
    fun start()
    fun stop()
}

class Controllers  internal constructor(
    sensorOrientationController: SensorOrientationStartStop //, GPSManager: GPSManager
) : StartStop {
    private val controllers = ArrayList<StartStop>()
    private val sensorOrientationController: SensorOrientationStartStop

    override fun start() {
        for (controller in controllers) {
            controller.start()
        }
    }

    override fun stop() {
        for (controller in controllers) {
            controller.stop()
        }
    }

    fun addController(startStop: StartStop) {
        controllers.add(startStop)
    }


    init {
      //  addController(GPSManager)
        this.sensorOrientationController = sensorOrientationController
        addController(sensorOrientationController)
    }
}