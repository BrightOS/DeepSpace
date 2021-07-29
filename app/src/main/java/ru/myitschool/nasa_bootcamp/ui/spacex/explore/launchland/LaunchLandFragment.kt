package ru.myitschool.nasa_bootcamp.ui.spacex.explore.launchland

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R

@AndroidEntryPoint
class LaunchLandFragment : Fragment() {

    private val launchLandViewModel: LaunchLandViewModel by viewModels<LaunchLandViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        launchLandViewModel.getViewModelScope().launch {
            launchLandViewModel.getLandPads()
            launchLandViewModel.getLaunchPads()
        }

        launchLandViewModel.getLandList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {

        })

        launchLandViewModel.getLaunchList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {

        })


        return inflater.inflate(R.layout.fragment_launch_land, container, false)
    }
}