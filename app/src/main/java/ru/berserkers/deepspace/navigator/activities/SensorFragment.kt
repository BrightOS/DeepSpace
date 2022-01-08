package ru.berserkers.deepspace.navigator.activities


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
import ru.berserkers.deepspace.R
import ru.berserkers.deepspace.databinding.FragmentSensorsBinding
import ru.berserkers.deepspace.navigator.pointing.AbstractPointing
import ru.berserkers.deepspace.navigator.pointing.VectorPointing
import java.util.*

class SensorFragment : Fragment(), SensorEventListener {
    lateinit var model: AbstractPointing

    private var _binding: FragmentSensorsBinding? = null
    private val binding get() = _binding!!

    private lateinit var sensorManager: SensorManager
    private var accelSensor: Sensor? = null
    private var magSensor: Sensor? = null
    private var gyroSensor: Sensor? = null
    private var rotationVectorSensor: Sensor? = null
    private var lightSensor: Sensor? = null
    private var temperatureSensor: Sensor? = null
    private var pressureSensor: Sensor? = null
    private var proximitySensor: Sensor? = null
    private var continueUpdates = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = VectorPointing()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSensorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sensorManager = ContextCompat.getSystemService(requireActivity().application, SensorManager::class.java)!!
    }


    override fun onResume() {
        super.onResume()
        onResumeSensors()
        continueUpdates = true
    }

    private fun onResumeSensors() {
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val nosensorColor = resources.getColor(R.color.none)

        sensorManager.also {
            getAccelerometerData(nosensorColor, it)
            getCompassData(it, nosensorColor)
            getGyroData(it, nosensorColor)
            getRotationVectorData(it, nosensorColor)
            getLightData(it, nosensorColor)
            getTemperatureData(it, nosensorColor)
            getPressureData(it, nosensorColor)
            getProximityData(it, nosensorColor)
        }
    }

    private fun getProximityData(it: SensorManager, nosensorColor: Int) {
        proximitySensor = it.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        if (proximitySensor == null) {
            binding.proximity.setTextColor(nosensorColor)
        } else {
            it.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun getPressureData(it: SensorManager, nosensorColor: Int) {
        pressureSensor = it.getDefaultSensor(Sensor.TYPE_PRESSURE)
        if (pressureSensor == null) binding.temperature.setTextColor(nosensorColor)
        else it.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private fun getTemperatureData(it: SensorManager, nosensorColor: Int) {
        temperatureSensor = it.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if (temperatureSensor == null)
            binding.temperature.setTextColor(nosensorColor)
        else it.registerListener(
            this,
            temperatureSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    private fun getLightData(it: SensorManager, nosensorColor: Int) {
        lightSensor = it.getDefaultSensor(Sensor.TYPE_LIGHT)
        if (lightSensor == null) {
            binding.light.setTextColor(nosensorColor)
        } else {
            it.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun getRotationVectorData(it: SensorManager, nosensorColor: Int) {
        rotationVectorSensor = it.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

        if (rotationVectorSensor == null) {
            binding.rotation.setTextColor(nosensorColor)
        } else {
            it.registerListener(
                this,
                rotationVectorSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    private fun getGyroData(it: SensorManager, nosensorColor: Int) {
        gyroSensor = it.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if (gyroSensor == null) {
            binding.gyro.setTextColor(nosensorColor)
        } else {
            it.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun getCompassData(it: SensorManager, nosensorColor: Int) {
        magSensor = it.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        if (magSensor == null) {
            binding.compass.setTextColor(nosensorColor)
        } else {
            it.registerListener(this, magSensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun getAccelerometerData(nosensorColor: Int, it: SensorManager) {
        if (accelSensor == null) {
            binding.accelerometer.setTextColor(nosensorColor)
        } else {
            it.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL)
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
                SensorManager.SENSOR_STATUS_UNRELIABLE -> view.setTextColor(resources.getColor(R.color.vaerybad))
                SensorManager.SENSOR_STATUS_ACCURACY_LOW -> view.setTextColor(resources.getColor(R.color.bad))
                SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM -> view.setTextColor(resources.getColor(R.color.norm))
                SensorManager.SENSOR_STATUS_ACCURACY_HIGH -> view.setTextColor(resources.getColor(R.color.great))
                SensorManager.SENSOR_STATUS_NO_CONTACT -> view.setTextColor(resources.getColor(R.color.vaerybad))
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

        binding.apply {
            when (sensor) {
                accelSensor -> accelerometer.text = valuesText
                magSensor -> compass.text = valuesText
                gyroSensor -> gyro.text = valuesText
                rotationVectorSensor -> rotation.text = valuesText
                lightSensor -> light.text = valuesText
                temperatureSensor -> temperature.text = valuesText
                pressureSensor -> pressure.text = valuesText
                proximitySensor -> proximity.text = valuesText

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
                        0 -> rotationMatrix1.text = valuesText
                        1 -> rotationMatrix2.text = valuesText
                        2 -> rotationMatrix3.text = valuesText
                        else -> rotationMatrix3.text = valuesText
                    }

                    val rowValues = FloatArray(3)
                    System.arraycopy(matrix, row * 3, rowValues, 0, 3)
                    valuesText.setLength(valuesText.length - 1)
                }
            }
        }
    }
}
