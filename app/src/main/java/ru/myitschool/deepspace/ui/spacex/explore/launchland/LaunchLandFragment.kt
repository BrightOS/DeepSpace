package ru.myitschool.deepspace.ui.spacex.explore.launchland

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.deepspace.R
import ru.myitschool.deepspace.databinding.FragmentLaunchLandBinding
import ru.myitschool.deepspace.utils.DimensionsUtil
import ru.myitschool.deepspace.utils.MarginItemDecoration

@AndroidEntryPoint
class LaunchLandFragment : Fragment() {

    private val launchLandViewModel: LaunchLandViewModel by viewModels<LaunchLandViewModelImpl>()

    private var _binding: FragmentLaunchLandBinding? = null
    private val binding get() = _binding!!
    private lateinit var landAdapter: LandAdapter
    private lateinit var launchAdapter: LaunchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchLandBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            launchLandViewModel.apply {
                getViewModelScope().launch {
                    getLandPads()
                    getLaunchPads()
                }
            }
            setupNavigation()
            setupRecycler()
            setupLaunchLandButtons()
        }
    }

    private fun FragmentLaunchLandBinding.setupLaunchLandButtons() {
        mapLandButton.setOnClickListener {
            launchLandRecycler.adapter = landAdapter
            title.text = getString(R.string.land_pads)
        }

        mapLaunchButton.setOnClickListener {
            launchLandRecycler.adapter = launchAdapter
            title.text = getString(R.string.launch_pads)
        }
    }

    private fun FragmentLaunchLandBinding.setupRecycler() {
        launchLandRecycler.setHasFixedSize(true)
        launchLandRecycler.addItemDecoration(
            MarginItemDecoration(DimensionsUtil.dpToPx(requireContext(), 10))
        )
    }

    private fun FragmentLaunchLandBinding.setupNavigation() {
        val navController = findNavController()

        launchLandViewModel.getLandList().observe(viewLifecycleOwner, {
            landAdapter =
                LandAdapter(
                    launchLandViewModel.getLandList().value!!,
                    navController
                )
        })

        launchLandViewModel.getLaunchList()
            .observe(viewLifecycleOwner, {
                launchAdapter =
                    LaunchAdapter(
                        launchLandViewModel.getLaunchList().value!!,
                        navController
                    )

                launchLandRecycler.adapter = launchAdapter
            })
    }
}