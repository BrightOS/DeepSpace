package ru.myitschool.nasa_bootcamp.ui.spacex.explore.launchland

import android.content.Intent
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
import ru.myitschool.nasa_bootcamp.MainActivity
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.FragmentLaunchLandBinding
import ru.myitschool.nasa_bootcamp.databinding.FragmentMapsBinding


data class NameDetails(val name: String, val details: String)

class MapsFragment : Fragment() {
    var latitude: Double? = null
    var longitude: Double? = null
    var name: String? = null
    var details: String? = null
    var nameDetails: NameDetails? = null

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name")
            longitude = it.getFloat("longitude").toDouble()
            latitude = it.getFloat("latitude").toDouble()
            details = it.getString("details")
        }
        nameDetails = NameDetails(name!!, details!!)
    }


    private val callback = OnMapReadyCallback { googleMap ->

        val landingZone = LatLng(latitude!!, longitude!!)

        googleMap.addMarker(MarkerOptions().position(landingZone).title(name)).apply {
            tag = nameDetails
        }
        googleMap.setInfoWindowAdapter(MarkerInfoAdapter(requireContext()))

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(landingZone, 15f), 2000, null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


}