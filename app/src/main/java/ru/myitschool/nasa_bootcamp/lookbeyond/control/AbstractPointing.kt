package ru.myitschool.nasa_bootcamp.lookbeyond.control


import ru.myitschool.nasa_bootcamp.lookbeyond.Math.GeocentricCoord
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.LatLong
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.Vector3D
import java.util.*

interface AbstractPointing {
    //Для отслеживания куда направлен телефон
    class Pointing constructor(
        lookDirection: GeocentricCoord = GeocentricCoord(1.0, 0.0, 0.0),
        perpendicular: GeocentricCoord =
            GeocentricCoord(0.0, 1.0, 0.0)
    ) {
        private val lookDir = lookDirection

        private val perpendicular: GeocentricCoord


        fun getLookDirection(): GeocentricCoord {
            return lookDir.copy()
        }

        fun getPerpendicular(): GeocentricCoord {
            return perpendicular.copy()
        }


        fun updatePerpendicular(newPerpendicular: Vector3D?) {
            perpendicular.setupVetor(newPerpendicular!!)
        }

        fun updateLookDir(newLineOfSight: Vector3D?) {
            lookDir.setupVetor(newLineOfSight!!)
        }

        init {
            this.perpendicular = perpendicular.copy()
        }
    }

    var viewOfUser: Double

    fun setHorizontalRotation(value: Boolean)

    val time: Date?

    val currentTimeMillis: Long

    var currentLocation: LatLong?

    val pointing: Pointing?

    fun setPointing(lineOfSight: Vector3D, perpendicular: Vector3D)

    val phoneUpDirVector: Vector3D

    fun setPhoneSensorValues(rotationVector: FloatArray)

    val zenit: GeocentricCoord?

    val timeMillis: Long
}