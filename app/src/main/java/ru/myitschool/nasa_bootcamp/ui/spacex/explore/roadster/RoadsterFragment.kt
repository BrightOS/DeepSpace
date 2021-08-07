package ru.myitschool.nasa_bootcamp.ui.spacex.explore.roadster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.FragmentInfoBinding
import ru.myitschool.nasa_bootcamp.databinding.FragmentMarsRoversBinding
import ru.myitschool.nasa_bootcamp.databinding.FragmentRoadsterBinding
import ru.myitschool.nasa_bootcamp.utils.*
import ru.myitschool.nasa_bootcamp.utils.TESLA_ROADSTER_ORBIT

@AndroidEntryPoint
class RoadsterFragment : Fragment() {
    private val roadsterViewModel: RoadsterViewModel by viewModels<RoadsterViewModelImpl>()
    private var _binding: FragmentRoadsterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoadsterBinding.inflate(inflater, container, false)

        loadImage(requireContext(), TESLA_ROADSTER_ORBIT, binding.roadsterOrbitGif)
        loadImage(requireContext(), STARMAN_1, binding.roadsterGif)
        loadImage(requireContext(), STARMAN_2, binding.roadsterGif2)
        loadImage(requireContext(), STARMAN_3, binding.roadsterGif3)

        roadsterViewModel.getViewModelScope().launch {
            roadsterViewModel.getRoadsterInfo()
        }

        roadsterViewModel.getRoadsterModel()
            .observe(viewLifecycleOwner, {
                binding.earthDistanceRoadster.text =
                    "Distance from Earth(km): ${roadsterViewModel.getRoadsterModel().value!!.earth_distance_km}"
                binding.marsDistanceRoadster.text =
                    "Distance from Mars(km): ${roadsterViewModel.getRoadsterModel().value!!.mars_distance_km}"
                binding.launchDateRoadster.text =
                    "Launch date: ${convertDateFromUnix(roadsterViewModel.getRoadsterModel().value!!.launch_date_unix)}"
                binding.launchMassKgRoadster.text =
                    "Launch mass(kg): ${roadsterViewModel.getRoadsterModel().value!!.launch_mass_kg}"
                binding.longitudeRoadster.text =
                    "Longtitude: ${roadsterViewModel.getRoadsterModel().value!!.longitude}"
                binding.nameRoadster.text =
                    "${roadsterViewModel.getRoadsterModel().value!!.name}"
                binding.orbitTypeRoadster.text =
                    "Orbit type : ${roadsterViewModel.getRoadsterModel().value!!.orbit_type}"
                binding.detailsRoadster.text =
                    "${roadsterViewModel.getRoadsterModel().value!!.details}"
            })

        return binding.root
    }

}