package ru.myitschool.nasa_bootcamp.ui.mars_rovers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ru.myitschool.nasa_bootcamp.databinding.FragmentRoverDetailsBinding


class RoverDetails : Fragment() {
    private var _binding: FragmentRoverDetailsBinding? = null
    private val binding get() = _binding!!

    var name: String? = null
    var landing_date: String? = null
    var launch_date: String? = null
    var status: String? = null
    var camera: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name")
            landing_date = it.getString("landing_date")
            launch_date = it.getString("launch_date")
            status = it.getString("status")
            camera = it.getString("camera")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRoverDetailsBinding.inflate(inflater, container, false)

        binding.camera.text = "Camera: $camera"
        binding.roverName.text = "Rover name : $name"
        binding.landingDate.text = "Landing date : $landing_date"
        binding.launchDate.text = "Launching date : $launch_date"
        binding.status.text = "Status : $status"

        return binding.root
    }
}