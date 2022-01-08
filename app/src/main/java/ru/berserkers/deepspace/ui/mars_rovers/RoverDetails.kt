package ru.berserkers.deepspace.ui.mars_rovers

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ru.berserkers.deepspace.databinding.FragmentRoverDetailsBinding

/*
 * @author Yana Glad
 */
class RoverDetails : Fragment() {
    private var _binding: FragmentRoverDetailsBinding? = null
    private val binding get() = _binding!!

    var name: String? = null
    private var landingDate: String? = null
    private var launchDate: String? = null
    var status: String? = null
    private var camera: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name")
            landingDate = it.getString("landing_date")
            launchDate = it.getString("launch_date")
            status = it.getString("status")
            camera = it.getString("camera")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoverDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    @SuppressLint("SetTextI18n")
    private fun setupData() {
        binding.camera.text = "Camera: $camera"
        binding.roverName.text = "Rover name : $name"
        binding.landingDate.text = "Landing date : $landingDate"
        binding.launchDate.text = "Launching date : $launchDate"
        binding.status.text = "Status : $status"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}