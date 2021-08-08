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
import java.util.*

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
                    "${roadsterViewModel.getRoadsterModel().value!!.earth_distance_km}"
                binding.marsDistanceRoadster.text =
                    "${roadsterViewModel.getRoadsterModel().value!!.mars_distance_km}"
                binding.launchMassKgRoadster.text =
                    "${roadsterViewModel.getRoadsterModel().value!!.launch_mass_kg}"
                binding.longitudeRoadster.text =
                    "${roadsterViewModel.getRoadsterModel().value!!.longitude}"
                binding.nameRoadster.text =
                    roadsterViewModel.getRoadsterModel().value!!.name
                binding.orbitTypeRoadster.text =
                    roadsterViewModel.getRoadsterModel().value!!.orbit_type
                binding.detailsRoadster.text =
                    roadsterViewModel.getRoadsterModel().value!!.details

                val finalString = convertDateFromUnix(roadsterViewModel.getRoadsterModel().value?.launch_date_unix!!)

                val calendar = GregorianCalendar()
                calendar.time = Date(roadsterViewModel.getRoadsterModel().value!!.launch_date_unix * 1000L)

                binding.launchDateRoadster.text = finalString.addSubstringAtIndex(
                    getDayOfMonthSuffix(
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ),
                    finalString.indexOf('.')
                )
            })


        return binding.root
    }

}