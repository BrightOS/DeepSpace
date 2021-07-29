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
import ru.myitschool.nasa_bootcamp.utils.convertDateFromUnix
import ru.myitschool.nasa_bootcamp.utils.loadImage

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

        context?.let { loadImage(it, "https://media1.tenor.com/images/3e4be755dbd97b74ceab4721d6f935c3/tenor.gif?itemid=11008638", binding.roadsterGif) }
        context?.let { loadImage(it, "https://media1.tenor.com/images/56beadf4c8c6646591c0dfb33989c2d9/tenor.gif?itemid=12084001", binding.roadsterGif2) }
        context?.let { loadImage(it, "https://avatars.mds.yandex.net/get-zen_doc/26916/pub_5c66833030a74d00ae3c1147_5c668334d867ce00ae722ce7/orig", binding.roadsterGif3) }

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