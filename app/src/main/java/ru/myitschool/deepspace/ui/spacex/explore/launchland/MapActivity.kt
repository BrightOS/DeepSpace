package ru.myitschool.deepspace.ui.spacex.explore.launchland

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.myitschool.deepspace.R
import ru.myitschool.deepspace.databinding.ActivityMapBinding


class MapActivity : AppCompatActivity() {

    var latitude: Double? = null
    var longitude: Double? = null
    var name: String? = null
    var details: String? = null
    private var nameDetails: NameDetails? = null

    val args: MapActivityArgs by navArgs()

    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        args.let {
            name = it.name
            longitude = it.longitude.toDouble()
            latitude = it.latitude.toDouble()
            details = it.details
        }
        nameDetails = NameDetails(name!!, details!!)

        val callback = OnMapReadyCallback { googleMap ->
            val landingZone = LatLng(latitude!!, longitude!!)

            googleMap.addMarker(MarkerOptions().position(landingZone).title(name))?.apply {
                tag = nameDetails
            }
            googleMap.setInfoWindowAdapter(MarkerInfoAdapter(this))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(landingZone, 15f), 2000, null)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fr) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    data class NameDetails(val name: String, val details: String)
}