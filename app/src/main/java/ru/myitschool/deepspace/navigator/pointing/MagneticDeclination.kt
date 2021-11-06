package ru.myitschool.deepspace.navigator.pointing

import android.hardware.GeomagneticField
import ru.myitschool.deepspace.maths.coords.LatLong


class MagneticDeclination {
    private var geomagneticField: GeomagneticField? = null

    fun setLocationAndTime(location: LatLong, time: Long) {
        geomagneticField = GeomagneticField(
            location.latitude.toFloat(),
            location.longitude.toFloat(),
            0f,
            time
        )
    }

    val declination: Double
        get() {
            return if (geomagneticField == null) 0.0 else geomagneticField!!.declination.toDouble()
        }
}