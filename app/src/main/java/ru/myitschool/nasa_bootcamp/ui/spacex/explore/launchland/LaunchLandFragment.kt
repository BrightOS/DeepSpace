package ru.myitschool.nasa_bootcamp.ui.spacex.explore.launchland

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.databinding.FragmentLaunchLandBinding

@AndroidEntryPoint
class LaunchLandFragment : Fragment() {

    private val launchLandViewModel: LaunchLandViewModel by viewModels<LaunchLandViewModelImpl>()

    private var _binding: FragmentLaunchLandBinding? = null
    private val binding get() = _binding!!
    private lateinit var landAdapter: LandAdapter

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

        launchLandViewModel.getLandList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            landAdapter =
                LandAdapter(
                    requireContext(),
                    launchLandViewModel.getLandList().value!!
                )
            binding.launchLandRecycler.adapter = landAdapter
        })

        launchLandViewModel.getLaunchList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {

        })

     //   val navController = findNavController()


        binding.mapLandButton.setOnClickListener(View.OnClickListener {
//            val bundle = Bundle();
//            Log.d("NAME_TAG", "Name is ${roverModel.rover.name}")
//            bundle.putString("name", roverModel.rover.name)
//            bundle.putString("landing_date", roverModel.rover.landing_date)
//            bundle.putString("launch_date", roverModel.rover.launch_date)
//            bundle.putString("status", roverModel.rover.status)
//            bundle.putString("camera", roverModel.camera.fullname)
//
//            navController.navigate(R.id.map_fragment, bundle)
//

           // val action = LaunchLandFragmentDirections.actionLaunchLandFragmentToMapsFragment()
           // navController.navigate(action)
        })

        return binding.root
    }
}