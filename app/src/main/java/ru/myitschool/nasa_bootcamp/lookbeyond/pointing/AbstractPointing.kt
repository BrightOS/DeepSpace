package ru.myitschool.nasa_bootcamp.lookbeyond.pointing


import ru.myitschool.nasa_bootcamp.lookbeyond.maths.GeocentricCoord
import ru.myitschool.nasa_bootcamp.lookbeyond.maths.LatLong
import ru.myitschool.nasa_bootcamp.lookbeyond.maths.Vector3D
import java.util.*

interface AbstractPointing {
    //Для отслеживания куда направлен телефон
    class Pointing  constructor(
        lineOfSight: GeocentricCoord = GeocentricCoord(1.0, 0.0, 0.0),
        perpendicular: GeocentricCoord =
            GeocentricCoord(0.0, 1.0, 0.0)
    ) {
        private val lookDir: GeocentricCoord
        private val perpendicular: GeocentricCoord

        val lineOfSightX: Double
            get() = lookDir.x
        val lineOfSightY: Double
            get() = lookDir.y
        val lineOfSightZ: Double
            get() = lookDir.z
        val perpendicularX: Double
            get() = perpendicular.x
        val perpendicularY: Double
            get() = perpendicular.y
        val perpendicularZ: Double
            get() = perpendicular.z

        fun updatePerpendicular(newPerpendicular: Vector3D) {
            perpendicular.setupVector(newPerpendicular)
        }

        fun updateLookDir(lookDir: Vector3D) {
            this.lookDir.setupVector(lookDir)
        }

        init {
            this.lookDir = lineOfSight.copy()
            this.perpendicular = perpendicular.copy()
        }
    }

    var fieldOfView: Double


    val time: Date

    var location: LatLong

    //направление
    val pointing: Pointing

    fun setPointing(lookDir: Vector3D, perpendicular: Vector3D)
    //Вектор ускорения
    val phoneUpDirection: Vector3D

    // Ускорение
    fun setPhoneSensorValues(acceleration: Vector3D, magneticField: Vector3D)

    //вектор поворота телефона
    fun setPhoneSensorValues(rotationVector: FloatArray)


     val timeMillis: Long
}