package ru.myitschool.nasa_bootcamp.lookbeyond.pointing

import android.hardware.SensorManager
import ru.myitschool.nasa_bootcamp.lookbeyond.maths.*
import ru.myitschool.nasa_bootcamp.lookbeyond.maths.Matrix3D.Companion.matrixE

import java.util.*


class VectorPointing() :
    AbstractPointing {

    private var fieldOfViewModel = 45.0 // Degrees
    private var locationModel = LatLong(0.0, 0.0)
    private var timeInMillisSinceEpoch: Long = System.currentTimeMillis()


    // Cодержит вектор на экране телефона
    //  в геоц координатах и перпендикулярный вектор вдоль высоты телефона.
    private val pointingM = AbstractPointing.Pointing()

    //Датчик ускорения
    private val acceleration = Vector3D(doubleArrayOf(0.0, -1.0, -9.0)) //север
    private var upPhone = scaleVector(acceleration, -1.0)

    //магнитный датчик
    private val magneticField = Vector3D(doubleArrayOf(0.0, -1.0, 0.0)) //юг
    private var rotateViaVector = false
    private val rotationVector = floatArrayOf(1f, 0f, 0f, 0f)

    private var axesPhoneInverseMatrix = matrixE

    private var axesMagneticCelestialMatrix = matrixE

    override val time: Date
        get() = Date(timeInMillisSinceEpoch)


    override var location: LatLong
        get() = locationModel
        set(value) {
            this.locationModel = value
        }

    override val pointing: AbstractPointing.Pointing
        get() {
            calculatePointing()
            return pointingM
        }

    override var fieldOfView: Double = fieldOfViewModel
        get() = fieldOfViewModel
        set(value) {
            field = value
        }


    override val phoneUpDirection
        get() = upPhone


    override fun setPhoneSensorValues(acceleration: Vector3D, magneticField: Vector3D) {
        this.acceleration.setupVector(acceleration)
        this.magneticField.setupVector(magneticField)
        rotateViaVector = false
    }

    override fun setPhoneSensorValues(rotationVector: FloatArray) {
        System.arraycopy(
            rotationVector,
            0,
            this.rotationVector,
            0,
            Math.min(rotationVector.size, 4)
        )
        rotateViaVector = true
    }


    override val timeMillis: Long
        get() = timeInMillisSinceEpoch


    ///  Обновляет pointing, то есть направление,
    // / в котором находится телефон в системе небесных коорд
    //// и вектор вдоль выосты экрана

    private fun calculatePointing() {
        calculateLocalNorthAndUpInPhoneCoordsFromSensors()
        val transform = matrixMultiply(axesMagneticCelestialMatrix, axesPhoneInverseMatrix)
        val viewInSpaceSpace = matrixVectorMultiplyScalars(transform, Vector3D(0.0, 0.0, -1.0)) //direction
        val screenUpInSpaceSpace = matrixVectorMultiplyScalars(transform, Vector3D(0.0, 1.0, 0.0)) //вверх
        pointingM.updateLookDir(viewInSpaceSpace)
        pointingM.updatePerpendicular(screenUpInSpaceSpace)
    }


    /// Вычисляет векторы с помочью магнитного датчика и акселерометра
    private fun calculateLocalNorthAndUpInPhoneCoordsFromSensors() {
        val magneticNorthPhone: Vector3D
        val magneticEastPhone: Vector3D
        if (rotateViaVector) {
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
            val down = acceleration.copy()
            down!!.normalize()

            val magneticFieldToNorth = magneticField.copy()
            magneticFieldToNorth!!.scale(-1.0)
            magneticFieldToNorth.normalize()

            magneticNorthPhone = sumVector(
                magneticFieldToNorth,
                scaleVector(down, -scalarMult(magneticFieldToNorth, down))
            )

            magneticNorthPhone.normalize()

            upPhone = scaleVector(down, -1.0)

            magneticEastPhone = vectorCross(magneticNorthPhone, upPhone)
        }

        axesPhoneInverseMatrix = Matrix3D(magneticNorthPhone, upPhone, magneticEastPhone)
    }


    override fun setPointing(lookDir: Vector3D, perpendicular: Vector3D) {
        pointingM.updateLookDir(lookDir)
        pointingM.updatePerpendicular(perpendicular)
    }

}