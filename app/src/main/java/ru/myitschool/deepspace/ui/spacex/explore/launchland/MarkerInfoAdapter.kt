package ru.myitschool.deepspace.ui.spacex.explore.launchland

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import ru.myitschool.deepspace.databinding.MarkerInfoBinding

class MarkerInfoAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }

    override fun getInfoContents(marker: Marker): View? {
        val zone = marker.tag as? MapActivity.NameDetails ?: return null
        val binding = MarkerInfoBinding.inflate(LayoutInflater.from(context))

        binding.placeName.text = zone.name
        binding.detailsMaps.text = zone.details

        return binding.root
    }
}