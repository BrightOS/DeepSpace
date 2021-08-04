package ru.myitschool.nasa_bootcamp.lookbeyond.pointing

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import javax.inject.Inject


class SensorOrientationStartStop internal constructor(
    var model: AbstractPointing,
    manager: SensorManager
) : StartStop, SensorEventListener {

    private val manager: SensorManager?
    private var accelerometerSmoother: SensorEventListener? = null
    private var compassSmoother: SensorEventListener? = null
    private val rotationSensor: Sensor

    override fun start() {
        manager!!.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun stop() {
        manager!!.unregisterListener(accelerometerSmoother)
        manager.unregisterListener(compassSmoother)
        manager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor != rotationSensor) {
            return
        }
        model.setPhoneSensorValues(event.values)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }


    init {
        this.manager = manager
        rotationSensor = manager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    }
}