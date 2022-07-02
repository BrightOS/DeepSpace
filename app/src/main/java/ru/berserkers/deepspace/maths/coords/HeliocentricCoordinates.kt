package ru.berserkers.deepspace.maths.coords

import com.example.math_module.astronomy.OrbitalElements
import com.example.math_module.geometry.Vector3D
import ru.berserkers.deepspace.navigator.rendertype.Planet
import ru.berserkers.deepspace.utils.OBLIQUITY
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

class HeliocentricCoordinates(var radius: Double, xh: Double, yh: Double, zh: Double, ) : Vector3D(xh, yh, zh) {

    fun heliocentric(): HeliocentricCoordinates {
        return HeliocentricCoordinates(
            radius,
            x,
            y * cos(OBLIQUITY) - z * sin(OBLIQUITY),
            y * sin(OBLIQUITY) + z * cos(OBLIQUITY)
        )
    }

    operator fun dec(other: HeliocentricCoordinates) : HeliocentricCoordinates {
        return HeliocentricCoordinates(radius, x - other.x,y - other.y, z - other.z)
    }

    operator fun minus(other: HeliocentricCoordinates): HeliocentricCoordinates {
        return HeliocentricCoordinates(radius, x - other.x,y - other.y, z - other.z)
    }

    companion object {

        fun getInstance(planet: Planet, date: Date?): HeliocentricCoordinates {
            return getInstance(planet.getOrbitalElements(date))
        }

        fun getInstance(elem: OrbitalElements): HeliocentricCoordinates {
            val ecc = elem.inclination
            val radius = elem.eccentricity * (1 - ecc * ecc) / (1 + ecc * cos(elem.anomaly))

            val perhileron = elem.perihelion
            val ascendingNode = elem.longitude
            val inclination = elem.ascnode
            val xh = radius * (cos(ascendingNode) * cos(elem.anomaly + perhileron - ascendingNode) - sin(ascendingNode) * sin(elem.anomaly + perhileron - ascendingNode) * cos(inclination))
            val yh = radius * (sin(ascendingNode) * cos(elem.anomaly + perhileron - ascendingNode) + (cos(ascendingNode) * sin(elem.anomaly + perhileron - ascendingNode) * cos(inclination)))
            val zh = radius * (sin(elem.anomaly + perhileron - ascendingNode) * sin(inclination))

            return HeliocentricCoordinates(radius, xh, yh, zh)
        }
    }
}
