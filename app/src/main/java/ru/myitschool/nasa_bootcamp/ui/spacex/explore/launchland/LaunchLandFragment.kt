package ru.myitschool.nasa_bootcamp.ui.spacex.explore.launchland

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.databinding.FragmentLaunchLandBinding
import ru.myitschool.nasa_bootcamp.utils.STARS_ANIMATED_BACKGROUND
import ru.myitschool.nasa_bootcamp.utils.loadImage

@AndroidEntryPoint
class LaunchLandFragment : Fragment() {

    private val launchLandViewModel: LaunchLandViewModel by viewModels<LaunchLandViewModelImpl>()

    private var _binding: FragmentLaunchLandBinding? = null
    private val binding get() = _binding!!
    private lateinit var landAdapter: LandAdapter
    private lateinit var launchAdapter: LaunchAdapter

    internal lateinit var onLandClickListener: LandAdapter.OnLandPadClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchLandBinding.inflate(inflater, container, false)

        launchLandViewModel.getViewModelScope().launch {
            launchLandViewModel.getLandPads()
            launchLandViewModel.getLaunchPads()
        }

        binding.launchLandRecycler.setHasFixedSize(true)
        binding.launchLandRecycler.layoutManager = GridLayoutManager(context, 1)

        loadImage(requireContext(), STARS_ANIMATED_BACKGROUND, binding.launchLandBackground)

        val navController = findNavController()

        launchLandViewModel.getLandList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            landAdapter =
                LandAdapter(
                    requireContext(),
                    launchLandViewModel.getLandList().value!!,
                    navController
                )
            binding.launchLandRecycler.adapter = landAdapter

        })

        launchLandViewModel.getLaunchList()
            .observe(viewLifecycleOwner, {
                launchAdapter =
                    LaunchAdapter(
                        requireContext(),
                        launchLandViewModel.getLaunchList().value!!,
                        navController
                    )
            })

        binding.mapLandButton.setOnClickListener(View.OnClickListener {
            binding.launchLandRecycler.adapter = landAdapter
        })

        binding.mapLaunchButton.setOnClickListener(View.OnClickListener {
            binding.launchLandRecycler.adapter = launchAdapter
        })


        return binding.root
    }
}