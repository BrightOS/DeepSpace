package ru.myitschool.nasa_bootcamp.lookbeyond.managers

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager


class SensorManager internal
constructor(private val manager: SensorManager) :
    Manager, SensorEventListener {
    private val rotationSensor: Sensor = manager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    override fun start() {
        manager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun stop() {
        manager.unregisterListener(this)
    }

    override var model: AbstractPointing? = null
        get() = field
        set(value) {
            field = value
        }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor != rotationSensor) {
            return
        }
        model!!.setPhoneSensorValues(event.values)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }

}
