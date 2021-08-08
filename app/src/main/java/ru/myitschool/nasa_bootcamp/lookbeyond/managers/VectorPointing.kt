package ru.myitschool.nasa_bootcamp.lookbeyond.managers

import android.hardware.SensorManager
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.*

import java.util.*
import kotlin.math.abs
import kotlin.math.min


class VectorPointing :
    AbstractPointing {
    private var screenPhoneCoords = Vector3D(0.0, 1.0, 0.0) //телефон вверх
    private val pointingM = AbstractPointing.Pointing()
    private var locationModel = LatLong(0.0, 0.0)


    var location: LatLong? = LatLong(0.0, 0.0)
        set(value) {
            field = value
            getNorthCoords(true)
        }
    override val pointing: AbstractPointing.Pointing
        get() {
            getPointing()
            return pointingM
        }

    override var currentTimeMillis = System.currentTimeMillis()
    override var currentLocation: LatLong?
        get() = locationModel
        set(value) {}

    private var celestialCoordsLastUpdated: Long = -1


    private val acceleration = Vector3D(0.0, -1.0, -9.0) //север
    private var upPhone: Vector3D = scaleVector(acceleration, -1.0)

    private val magneticField = Vector3D(0.0, -1.0, 0.0) //Юг

    private var rotatingVector = false
    private val rotationVector = floatArrayOf(1.0f, 0.0f, 0.0f, 0.0f)

    private var northCoords = Vector3D(1.0, 0.0, 0.0)

    private var southCoords = Vector3D(0.0, 1.0, 0.0)

    private var eastCoords = Vector3D(0.0, 0.0, 1.0) //поворот земли

    private var phoneMatrix = Matrix3D.matrixE

    private var magneticMatrix = Matrix3D.matrixE

    override fun setHorizontalRotation(value: Boolean) {
        screenPhoneCoords = if (value) {
            Vector3D(1.0, 0.0, 0.0) //телефон вниз
        } else {
            Vector3D(0.0, 1.0, 0.0) //телефон вверх
        }
    }

    override val time: Date
        get() = Date(currentTimeMillis)


    override var viewOfUser: Double = 45.0


    override val zenit: GeocentricCoord
        get() {
            getNorthCoords(false)
            return GeocentricCoord.getInstanceFromVector3(southCoords)
        }


    override val phoneUpDirVector
        get() = upPhone


    override fun setPhoneSensorValues(rotationVector: FloatArray) {

        System.arraycopy(rotationVector, 0, this.rotationVector, 0, min(rotationVector.size, 4))
        rotatingVector = true
    }

    private fun getPointing() {
        getNorthCoords(false)
        getNorthFromSensors()

        val transform: Matrix3D =
            matrixMultiply(magneticMatrix, phoneMatrix)

        val viewInSpaceSpace: Vector3D = matrixVectorMultiply(transform, Vector3D(0.0, 0.0, -1.0))

        val screenUpInSpaceSpace: Vector3D = matrixVectorMultiply(transform, screenPhoneCoords)

        pointingM.updateLookDir(viewInSpaceSpace)
        pointingM.updatePerpendicular(screenUpInSpaceSpace)
    }


    private fun getNorthCoords(forceUpdate: Boolean) {
        val currentTime = currentTimeMillis
        if (!forceUpdate && abs(currentTime - celestialCoordsLastUpdated) < 60000L) {
            return
        }
        celestialCoordsLastUpdated = currentTime

        val up: RaDec = zenitRaDec(time, location!!)

        southCoords = GeocentricCoord.getInstance(up)
        val z = Vector3D(0.0, 0.0, 1.0)
        val zDotu: Double = scalarMult(southCoords, z)

        northCoords = addVectors(z, scaleVector(southCoords, -zDotu))
        northCoords.normalize()
        eastCoords = vectorCross(northCoords, southCoords)

        val rotationMatrix: Matrix3D = calculateRotationMatrix(0.0)

        val magneticNorthCelestial: Vector3D = matrixVectorMultiply(rotationMatrix, northCoords)

        val magneticEastCelestial: Vector3D = vectorCross(magneticNorthCelestial, southCoords)
        magneticMatrix = Matrix3D(
            magneticNorthCelestial,
            southCoords,
            magneticEastCelestial
        )
    }

    private fun getNorthFromSensors() {
        val magneticNorthPhone: Vector3D
        val magneticEastPhone: Vector3D
        if (rotatingVector) {
            val rotationMatrix = FloatArray(9)
            SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector)

            magneticNorthPhone = Vector3D(
                rotationMatrix[3].toDouble(),
                rotationMatrix[4].toDouble(),
                rotationMatrix[5].toDouble()
            )
            upPhone = Vector3D(
                rotationMatrix[6].toDouble(),
                rotationMatrix[7].toDouble(),
                rotationMatrix[8].toDouble()
            )
            magneticEastPhone = Vector3D(
                rotationMatrix[0].toDouble(),
                rotationMatrix[1].toDouble(),
                rotationMatrix[2].toDouble()
            )
        } else {

            val down = acceleration
            down!!.normalize()

            val magneticFieldToNorth = magneticField
            magneticFieldToNorth!!.scale(-1.0)
            magneticFieldToNorth.normalize()

            magneticNorthPhone = addVectors(
                magneticFieldToNorth,
                scaleVector(down, -scalarMult(magneticFieldToNorth, down))
            )
            magneticNorthPhone.normalize()
            upPhone = scaleVector(down, -1.0)
            magneticEastPhone = vectorCross(magneticNorthPhone, upPhone)
        }

        phoneMatrix = Matrix3D(magneticNorthPhone, upPhone, magneticEastPhone)
    }

    override fun setPointing(lineOfSight: Vector3D, perpendicular: Vector3D) {
        pointingM.updateLookDir(lineOfSight)
        pointingM.updatePerpendicular(perpendicular)
    }

    override val timeMillis: Long
        get() = currentTimeMillis

}