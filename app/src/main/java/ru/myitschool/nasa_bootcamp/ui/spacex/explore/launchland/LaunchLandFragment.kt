package ru.myitschool.nasa_bootcamp.ui.spacex.explore.launchland

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.FragmentInfoBinding
import ru.myitschool.nasa_bootcamp.databinding.FragmentLaunchLandBinding
import ru.myitschool.nasa_bootcamp.ui.spacex.ExploreFragmentDirections

@AndroidEntryPoint
class LaunchLandFragment : Fragment() {

    private val launchLandViewModel: LaunchLandViewModel by viewModels<LaunchLandViewModelImpl>()

    private var _binding: FragmentLaunchLandBinding? = null
    private val binding get() = _binding!!

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

        launchLandViewModel.getLandList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {

        })

        launchLandViewModel.getLaunchList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {

        })

        val navController = findNavController()


        binding.mapButton.setOnClickListener(View.OnClickListener {
            val action = LaunchLandFragmentDirections.actionLaunchLandFragmentToMapsFragment()
            navController.navigate(action)
        })

        return binding.root
    }
}