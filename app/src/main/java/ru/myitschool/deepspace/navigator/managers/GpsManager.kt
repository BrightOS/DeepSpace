package ru.myitschool.deepspace.navigator.managers

import android.content.Context
import android.location.*
import android.os.Bundle
import android.widget.Toast
import ru.myitschool.deepspace.maths.coords.LatLong
import ru.myitschool.deepspace.navigator.control.VectorPointing
import java.io.IOException
import java.util.*


class GpsManager(private val context: Context, private val locationManager: LocationManager?) :
    AbstractManager, LocationListener {
    override var pointing: VectorPointing? = null

    override fun start() {
        try {
            if (locationManager == null) {
                defaultLocation()
                return
            }
            val locationCriteria = Criteria()
            locationCriteria.apply {
                accuracy = Criteria.ACCURACY_COARSE
                isAltitudeRequired = false
                isBearingRequired = false
                isCostAllowed = true
                isSpeedRequired = false
                powerRequirement = Criteria.POWER_LOW
            }

            val locationProvider = locationManager.getBestProvider(locationCriteria, true)

            if (locationProvider == null) {
                val provider = locationManager.getBestProvider(locationCriteria, false)
                if (provider == null) {
                    defaultLocation()
                    return
                }
                return
            }
            locationManager.requestLocationUpdates(
                locationProvider,
                600000,
                2000f,
                this
            )
            val location = locationManager.getLastKnownLocation(locationProvider)
            if (location != null) {
                val myLocation = LatLong(location.latitude, location.longitude)
                changeLocation(myLocation)
            }
        } catch (securityException: SecurityException) {
        }
    }

    private fun defaultLocation() {
        val longitude = 0.0
        val latitude = 0.0

        val location = Location("location")
        location.latitude = latitude
        location.longitude = longitude

        val pos = LatLong(latitude, longitude)
        changeLocation(pos)
    }

    private fun changeLocation(location: LatLong) {
        val oldLocation = pointing?.location
        if (location.distanceFrom(oldLocation!!) > 0.01f) {
            showLocationToUser(location)
        }
        pointing!!.location = location
    }


    override fun stop() {
        if (locationManager == null) return
        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        val newLocation = LatLong(location.latitude, location.longitude)
        changeLocation(newLocation)
        locationManager!!.removeUpdates(this)
    }

    override fun onProviderDisabled(provider: String) {
    }

    override fun onProviderEnabled(provider: String) {
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
    }


    private fun showLocationToUser(location: LatLong) {
        val geoCoder = Geocoder(context)
        var addresses: List<Address?>? = ArrayList()
        val place: String?
        try {
            addresses = geoCoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )
        } catch (e: IOException) {
        }
        var message = "None"
        if (addresses != null) {
            place = getLocationName(location, addresses[0])
            message = "Location set to $place"
        }

        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun getLocationName(location: LatLong, address: Address?): String {
        val longLat = "${location.longitude} ${location.latitude}"
        if (address == null) {
            return longLat
        }
        var place = address.locality
        if (place == null) place = address.subAdminArea
        if (place == null) place = address.adminArea
        if (place == null) place = longLat

        return place
    }

}