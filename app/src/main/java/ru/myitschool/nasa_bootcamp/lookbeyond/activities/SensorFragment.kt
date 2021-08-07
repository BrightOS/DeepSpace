package ru.myitschool.nasa_bootcamp.lookbeyond.activities

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.FragmentSensorsBinding
import ru.myitschool.nasa_bootcamp.lookbeyond.managers.AbstractPointing
import ru.myitschool.nasa_bootcamp.lookbeyond.managers.VectorPointing

import java.util.*

class SensorFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentSensorsBinding? = null
    private val binding get() = _binding!!

    private lateinit var sensorManager: SensorManager
    lateinit var model: AbstractPointing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = VectorPointing()
    }


    private var accelSensor: Sensor? = null
    private var magSensor: Sensor? = null
    private var gyroSensor: Sensor? = null
    private var rotationVectorSensor: Sensor? = null
    private var lightSensor: Sensor? = null
    private var temperatureSensor: Sensor? = null
    private var pressureSensor: Sensor? = null
    private var proximitySensor: Sensor? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSensorsBinding.inflate(inflater, container, false)
        sensorManager = ContextCompat.getSystemService(
            requireActivity().application,
            SensorManager::class.java
        )!!

        return binding.root
    }


    private var continueUpdates = false

    override fun onResume() {
        super.onResume()
        onResumeSensors()
        continueUpdates = true

    }

    private fun onResumeSensors() {
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val nosensorColor = resources.getColor(R.color.none)

        if (accelSensor == null) {
            binding.accelerometer.setTextColor(nosensorColor)
        } else {
            sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }


        magSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        if (magSensor == null) {
            binding.compass.setTextColor(nosensorColor)
        } else {
            sensorManager.registerListener(this, magSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if (gyroSensor == null) {
            binding.gyro.setTextColor(nosensorColor)
        } else {
            sensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

        if (rotationVectorSensor == null) {
            binding.rotation.setTextColor(nosensorColor)
        } else {
            sensorManager.registerListener(
                this,
                rotationVectorSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        if (lightSensor == null) {
            binding.light.setTextColor(nosensorColor)
        } else {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if (temperatureSensor == null)
            binding.temperature.setTextColor(nosensorColor)
        else sensorManager.registerListener(
            this,
            temperatureSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        if (pressureSensor == null) binding.temperature.setTextColor(nosensorColor)
        else sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL)

        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        if (proximitySensor == null) {
            binding.temperature.setTextColor(nosensorColor)
        } else {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }


    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        knownSensorAccuracies.add(sensor)

        var view: TextView? = null

        when (sensor) {
            accelSensor -> view = binding.accelerometer
            magSensor -> view = binding.compass
            gyroSensor -> view = binding.gyro
            rotationVectorSensor -> binding.rotation
            lightSensor -> view = binding.light
            temperatureSensor -> view = binding.temperature
            pressureSensor -> view = binding.pressure
            proximitySensor -> view = binding.proximity

            else -> return
        }


        val resources = resources
        if (view != null) {
            when (accuracy) {
                SensorManager.SENSOR_STATUS_UNRELIABLE ->
                    view.setTextColor(
                        resources.getColor(R.color.vaerybad)
                    )
                SensorManager.SENSOR_STATUS_ACCURACY_LOW -> view.setTextColor(
                    resources.getColor(R.color.bad)
                )
                SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM -> view.setTextColor(
                    resources.getColor(R.color.norm)
                )
                SensorManager.SENSOR_STATUS_ACCURACY_HIGH -> view.setTextColor(
                    resources.getColor(R.color.great)
                )
                SensorManager.SENSOR_STATUS_NO_CONTACT -> view.setTextColor(
                    resources.getColor(R.color.vaerybad)
                )
            }
        }
    }

    private val knownSensorAccuracies: MutableSet<Sensor> = HashSet()
    override fun onSensorChanged(event: SensorEvent) {
        val sensor = event.sensor

        val valuesText = StringBuilder()
        for (i in event.values.indices) {
            valuesText.append(String.format("%.2f", event.values[i]))

            if ((i + 1) < event.values.size) valuesText.append(" , ")
        }
        valuesText.setLength(valuesText.length - 1)

        when (sensor) {
            accelSensor -> binding.accelerometer.text = valuesText
            magSensor -> binding.compass.text = valuesText
            gyroSensor -> binding.gyro.text = valuesText
            rotationVectorSensor -> binding.rotation.text = valuesText
            lightSensor -> binding.light.text = valuesText
            temperatureSensor -> binding.temperature.text = valuesText
            pressureSensor -> binding.pressure.text = valuesText
            proximitySensor -> binding.proximity.text = valuesText

            else -> return
        }

        if (sensor == rotationVectorSensor) {
            val matrix = FloatArray(9)
            SensorManager.getRotationMatrixFromVector(matrix, event.values)

            for (row in 0..2) {
                val valuesText = StringBuilder()
                for (value in event.values) {
                    valuesText.append(String.format("%.2f", value))
                    valuesText.append(" , ")
                }

                when (row) {
                    0 -> binding.rotationMatrix1.text = valuesText
                    1 -> binding.rotationMatrix2.text = valuesText
                    2 -> binding.rotationMatrix3.text = valuesText
                    else -> binding.rotationMatrix3.text = valuesText
                }

                val rowValues = FloatArray(3)
                System.arraycopy(matrix, row * 3, rowValues, 0, 3)

                valuesText.setLength(valuesText.length - 1)
            }
        }
    }

}