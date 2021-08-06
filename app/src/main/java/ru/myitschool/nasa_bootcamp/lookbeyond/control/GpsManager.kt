package ru.myitschool.nasa_bootcamp.lookbeyond.control

import android.content.Context
import android.location.*
import android.os.Bundle
import android.widget.Toast
import ru.myitschool.nasa_bootcamp.lookbeyond.Math.LatLong
import java.io.IOException
import java.util.*


class GpsManager constructor(
    private val context: Context,
    private val locationManager: LocationManager?
) :
    Manager, LocationListener {

    override var model: AbstractPointing? = null
        set(value) {
            field = value
        }

    override fun start() {
        try {
            if (locationManager == null) {

                val longitude = 0.0
                val latitude = 0.0

                val location = Location("location")
                location.latitude = latitude
                location.longitude = longitude

                val pos = LatLong(latitude, longitude)
                setLocationInModel(pos, "location")

                return
            }
            val locationCriteria = Criteria()
            locationCriteria.accuracy = Criteria.ACCURACY_COARSE
            locationCriteria.isAltitudeRequired = false
            locationCriteria.isBearingRequired = false
            locationCriteria.isCostAllowed = true
            locationCriteria.isSpeedRequired = false
            locationCriteria.powerRequirement = Criteria.POWER_LOW
            val locationProvider = locationManager.getBestProvider(locationCriteria, true)

            if (locationProvider == null) {
                val provider = locationManager.getBestProvider(locationCriteria, false)
                if (provider == null) {
                    val longitude = 0.0
                    val latitude = 0.0

                    val location = Location("location")
                    location.latitude = latitude
                    location.longitude = longitude

                    val pos = LatLong(latitude, longitude)
                    setLocationInModel(pos, "location")
                    return
                }
                return
            }

            val location = locationManager.getLastKnownLocation(locationProvider)
            if (location != null) {
                val myLocation = LatLong(location.latitude, location.longitude)
                setLocationInModel(myLocation, location.provider)
            }
        } catch (securityException: SecurityException) {
        }
    }

    override fun stop() {
        if (locationManager == null) {
            return
        }
        locationManager.removeUpdates(this)
    }

    private fun setLocationInModel(location: LatLong, provider: String) {
        showLocationToUser(location, provider)
        model!!.currentLocation = location
    }

    override fun onLocationChanged(location: Location) {
        val newLocation = LatLong(location.latitude, location.longitude)
        setLocationInModel(newLocation, location.provider)
        locationManager!!.removeUpdates(this)
    }

    private fun showLocationToUser(location: LatLong, provider: String) {
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
        val savemepls = "Longitude: ${location.longitude}, Latitude: ${location.latitude}"

        if (address == null) {
            return savemepls
        }
        var place = address.locality
        if (place == null) {
            place = address.subAdminArea
        }
        if (place == null) {
            place = address.adminArea
        }
        if (place == null) {
            place = savemepls
        }
        return place
    }

    override fun onProviderDisabled(provider: String) {
    }

    override fun onProviderEnabled(provider: String) {
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
    }

}