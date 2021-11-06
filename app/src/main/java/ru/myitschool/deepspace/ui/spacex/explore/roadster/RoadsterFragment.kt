package ru.myitschool.deepspace.ui.spacex.explore.roadster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.deepspace.databinding.FragmentRoadsterBinding
import ru.myitschool.deepspace.utils.*
import java.util.*

@AndroidEntryPoint
class RoadsterFragment : Fragment() {
    private val roadsterViewModel: RoadsterViewModel by viewModels<RoadsterViewModelImpl>()
    private var _binding: FragmentRoadsterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoadsterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            loadImages()
            roadsterViewModel.getViewModelScope().launch {
                roadsterViewModel.getRoadsterInfo()
            }
            setupRoadsterData()
        }
    }

    private fun FragmentRoadsterBinding.setupRoadsterData() {
        roadsterViewModel.getRoadsterModel()
            .observe(viewLifecycleOwner, {
                roadsterViewModel.getRoadsterModel().value!!.also {
                    earthDistanceRoadster.text =
                        "${it.earth_distance_km}"
                    marsDistanceRoadster.text =
                        "${it.mars_distance_km}"
                    launchMassKgRoadster.text =
                        "${it.launch_mass_kg}"
                    longitudeRoadster.text =
                        "${it.longitude}"
                    nameRoadster.text =
                        it.name
                    orbitTypeRoadster.text =
                        it.orbit_type
                    detailsRoadster.text =
                        it.details
                }
                val finalString =
                    convertDateFromUnix(roadsterViewModel.getRoadsterModel().value?.launch_date_unix!!)

                val calendar = GregorianCalendar()
                calendar.time = Date(roadsterViewModel.getRoadsterModel().value!!.launch_date_unix * 1000L)

                launchDateRoadster.text = finalString.addSubstringAtIndex(
                    getDayOfMonthSuffix(
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ),
                    finalString.indexOf('.')
                )
            })
    }

    private fun FragmentRoadsterBinding.loadImages() {
        loadImage(requireContext(), TESLA_ROADSTER_ORBIT, roadsterOrbitGif)
        loadImage(requireContext(), STARMAN_1, roadsterGif)
        loadImage(requireContext(), STARMAN_2, roadsterGif2)
        loadImage(requireContext(), STARMAN_3, roadsterGif3)
    }
}
