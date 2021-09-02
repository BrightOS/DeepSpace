package ru.myitschool.nasa_bootcamp.ui.mars_rovers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.MainActivity
import ru.myitschool.nasa_bootcamp.databinding.FragmentMarsRoversBinding
import ru.myitschool.nasa_bootcamp.utils.DimensionsUtil

@AndroidEntryPoint
class MarsRoversFragment : Fragment() {
    private var _binding: FragmentMarsRoversBinding? = null
    private val viewModel: MarsRoversViewModel by viewModels<MarsRoversViewModelImpl>()
    lateinit var roverRecyclerAdapter: RoverRecyclerAdapter

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsRoversBinding.inflate(inflater, container, false)

        DimensionsUtil.dpToPx(requireContext(), 5).let {
            DimensionsUtil.setMargins(
                binding.toolBar,
                it,
                DimensionsUtil.getStatusBarHeight(resources) + it,
                it,
                it
            )
        }

        (activity as MainActivity).main_loading?.startLoadingAnimation()

        viewModel.getViewModelScope().launch {
            viewModel.loadRoverPhotos()
        }

        binding.roversRecycle.setHasFixedSize(true)
        binding.roversRecycle.layoutManager = GridLayoutManager(context, 1)
        val navController = findNavController()

        viewModel.getRoverModelsLiveData().observe(viewLifecycleOwner, {
            Log.d("GOT IT", "Doing... ${viewModel.getRoverModelsLiveData().value!![0].rover.name}")

            val list =viewModel.getRoverModelsLiveData().value!!
            list.shuffle()

            (activity as MainActivity).main_loading?.stopLoadingAnimation()

            roverRecyclerAdapter =
                RoverRecyclerAdapter(
                    requireContext(),
                    list,
                    navController
                )

            binding.roversRecycle.adapter = roverRecyclerAdapter
        })


        return binding.root
    }
}