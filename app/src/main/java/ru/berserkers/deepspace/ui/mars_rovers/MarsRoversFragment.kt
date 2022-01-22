package ru.berserkers.deepspace.ui.mars_rovers

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.berserkers.deepspace.MainActivity
import ru.berserkers.deepspace.databinding.FragmentMarsRoversBinding
import ru.berserkers.deepspace.utils.DimensionsUtil
import ru.berserkers.deepspace.utils.dismissBanner
import ru.berserkers.deepspace.utils.showBanner

/*
 * @author Denis Shaikhlbarin
 */
@AndroidEntryPoint
class MarsRoversFragment : Fragment() {

    private var _binding: FragmentMarsRoversBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MarsRoversViewModel by viewModels<MarsRoversViewModelImpl>()
    private lateinit var roverRecyclerAdapter: RoverRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMarsRoversBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeData()
        showBanner(activity as Activity)
    }

    private fun observeData() {
        viewModel.apply {
            getViewModelScope().launch {
                loadRoverPhotos()
            }
        }

        viewModel.getRoverModelsLiveData().observe(viewLifecycleOwner) {
            val list = viewModel.getRoverModelsLiveData().value!!
            list.shuffle()

            (activity as MainActivity).stopLoadingAnimation(false)

            roverRecyclerAdapter = RoverRecyclerAdapter(requireContext(), list, findNavController())

            binding.roversRecycle.adapter = roverRecyclerAdapter
        }
    }

    private fun init() {
        DimensionsUtil.dpToPx(requireContext(), 5).let {
            DimensionsUtil.setMargins(
                view = binding.toolBar,
                left = it,
                top = DimensionsUtil.getStatusBarHeight(resources) + it,
                right = it,
                bottom = it
            )
        }

        (activity as MainActivity).startLoadingAnimation()

        binding.roversRecycle.setHasFixedSize(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        dismissBanner()
    }
}
