package ru.myitschool.deepspace.navigator.pointing

import android.hardware.SensorManager
import ru.myitschool.deepspace.maths.astronomy.getZenith
import ru.myitschool.deepspace.maths.coords.GeocentricCoordinates.Companion.getInstance
import ru.myitschool.deepspace.maths.coords.LatLong
import ru.myitschool.deepspace.maths.matrix.Vector3D
import ru.myitschool.deepspace.maths.matrix.rotationMatrix
import ru.myitschool.deepspace.maths.matrix.scalarTimes
import ru.myitschool.deepspace.maths.matrix.scaleVector
import ru.myitschool.deepspace.navigator.pointing.AbstractPointing.Pointing
import ru.myitschool.deepspace.navigator.maths.Matrix3Dimension
import ru.myitschool.deepspace.navigator.maths.Matrix3Dimension.Companion.idMatrix
import ru.myitschool.deepspace.navigator.maths.multVector
import java.util.*
import kotlin.math.abs

class VectorPointing : AbstractPointing {
    private val acc = Vector3D(0.0, -1.0, -9.0)
    override var up = scaleVector(acc, -1.0)

    override var viewDegree = 45.0
    override var location = LatLong(0.0, 0.0)
        set(value) {
            field = value
            getNorthAndSouth(true)
        }

    override val time: Date
        get() = Date(System.currentTimeMillis())

    private var celestialCoordsLastUpdated: Long = -1
    private val pointing = Pointing()
    private val magneticField = Vector3D(0.0, -1.0, 0.0)
    private var rotationVector = floatArrayOf(1f, 0f, 0f, 0f)
    private var trueNorth = Vector3D(1.0, 0.0, 0.0)
    private var upCelestial = Vector3D(0.0, 1.0, 0.0)
    private var trueEast = Vector3D(0.0, 0.0, 1.0) //вращение земли
    private var inverseMatrix = idMatrix
    private var magneticMatrix = idMatrix
    private var rotationVectorEnabled = false

    private var screenIsUp = Vector3D(0.0, 1.0, 0.0)

    private var declination: MagneticDeclination? = null


    override fun setHorizontalRotation(value: Boolean) {
        screenIsUp = if (value) Vector3D(1.0, 0.0, 0.0) else Vector3D(0.0, 1.0, 0.0) //up
    }

    override fun setPhoneSensorValues(acceleration: Vector3D, magneticField: Vector3D) {
        if (magneticField.length() < EPS || acceleration.length() < EPS) return

        this.acc.update(acceleration)
        this.magneticField.update(magneticField)
        rotationVectorEnabled = false
    }

    override fun setPhoneSensorValues(rotationVector: FloatArray) {
        this.rotationVector = rotationVector.clone()
        rotationVectorEnabled = true
    }

    override fun setMagneticDeclination(calculator: MagneticDeclination) {
        declination = calculator
        getNorthAndSouth(true)
    }


    private fun updatePointing() {
        getNorthAndSouth(false)

        val north: Vector3D
        val east: Vector3D

        if (rotationVectorEnabled) {
            val rotationMatrix = FloatArray(9)
            SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector)

            north = Vector3D(rotationMatrix[3].toDouble(), rotationMatrix[4].toDouble(), rotationMatrix[5].toDouble())
            up = Vector3D(rotationMatrix[6].toDouble(), rotationMatrix[7].toDouble(), rotationMatrix[8].toDouble())
            east = Vector3D(rotationMatrix[0].toDouble(), rotationMatrix[1].toDouble(), rotationMatrix[2].toDouble())
        } else {
            acc.norma()
            magneticField
                .scale(-1.0)
                .norma()

            north = (magneticField + scaleVector(acc, -magneticField.scalarTimes(acc))).norma()
            up = scaleVector(acc, -1.0)
            east = north * up
        }
        inverseMatrix = Matrix3Dimension(north, up, east, false)

        val matr = magneticMatrix * inverseMatrix

        pointing.apply {
            updateUserLook(matr.multVector(Vector3D(0.0, 0.0, -1.0)))
            updatePerpendicular(matr.multVector(screenIsUp))
        }
    }

    private fun getNorthAndSouth(disable: Boolean) {
        val currentTime = System.currentTimeMillis()
        if (!disable && abs(currentTime - celestialCoordsLastUpdated) < UPDATE_FREQUENCY) return

        celestialCoordsLastUpdated = currentTime
        updateMagneticCorrection()
        val up = getZenith(time, location)
        upCelestial = getInstance(up)
        val axis = Vector3D(0.0, 0.0, 1.0)
        val scalarValue = upCelestial.scalarTimes(axis)

        initTrueCoords(
            axis + scaleVector(upCelestial, -scalarValue),
            trueNorth * upCelestial
        )

        magneticCorrection()
    }

    private fun initTrueCoords(
        trueNorth: Vector3D,
        trueEast: Vector3D,
    ) {
        this.trueNorth = trueNorth
        this.trueNorth.norma()
        this.trueEast = trueEast
    }

    private fun magneticCorrection() {
        // магнитная коррекция. Сдвиг небесных координат в противоположную сторону
        val rotationMatrix = rotationMatrix(declination!!.declination, upCelestial)
        val magneticNorth = rotationMatrix.multVector(trueNorth)
        magneticMatrix = Matrix3Dimension(
            magneticNorth,
            upCelestial,
            magneticNorth * upCelestial
        )
    }

    private fun updateMagneticCorrection() {
        declination!!.setLocationAndTime(location, timeMillis)
    }


    val userPointing: Pointing
        get() {
            updatePointing()
            return pointing
        }

    override fun setPointing(userLook: Vector3D, perpendicular: Vector3D) {
        pointing.apply {
            updateUserLook(userLook)
            updatePerpendicular(perpendicular)
        }
    }

    override val timeMillis: Long
        get() = System.currentTimeMillis()

    companion object {
        private const val UPDATE_FREQUENCY = 60000L
        private const val EPS = 0.01f
    }

    init {
        setMagneticDeclination(MagneticDeclination())
    }
}
