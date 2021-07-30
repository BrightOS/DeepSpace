package ru.myitschool.nasa_bootcamp.ui.spacex.explore.launchland

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.myitschool.nasa_bootcamp.R

class MapsFragment : Fragment() {
    var latitude: Double? = null
    var longitude: Double? = null
    var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name")
            longitude = it.getFloat("longitude").toDouble()
            latitude = it.getFloat("latitude").toDouble()
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->

        val landingZone = LatLng(latitude!!, longitude!!)
        googleMap.addMarker(MarkerOptions().position(landingZone).title(name))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(landingZone, 15f),2500,null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate( R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}