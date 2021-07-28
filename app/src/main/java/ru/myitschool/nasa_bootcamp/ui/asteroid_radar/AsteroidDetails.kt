package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.FragmentAsteroidDetailsBinding
import ru.myitschool.nasa_bootcamp.databinding.FragmentExploreBinding


class AsteroidDetails : Fragment() {

    private var _binding: FragmentAsteroidDetailsBinding? = null
    private val binding get() = _binding!!

    var absolute_magnitude: Double? = null
    var estimatedDiameter: Double? = null
    var kmPerHour: Double? = null
    var distanceFromEarth: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            absolute_magnitude = it.getDouble("absolute_magnitude")
            estimatedDiameter = it.getDouble("estimatedDiameter")
            kmPerHour = it.getDouble("kmPerHour")
            distanceFromEarth = it.getString("distanceFromEarth")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAsteroidDetailsBinding.inflate(inflater, container, false)

        binding.absoluteMagnitude.text = "Absolute magnitude : $absolute_magnitude"
        binding.estimatedDiameter.text = "Estimated diameter : $estimatedDiameter"
        binding.kmPerHour.text = "Kilometers/hour : $kmPerHour"
        binding.distanceFromEarth.text = "Distance from earth : $distanceFromEarth"

        return binding.root
    }


}