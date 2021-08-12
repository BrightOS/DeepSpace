package ru.myitschool.nasa_bootcamp.ui.spacex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.myitschool.nasa_bootcamp.databinding.FragmentExploreBinding
import ru.myitschool.nasa_bootcamp.utils.DimensionsUtil
import ru.myitschool.nasa_bootcamp.utils.SPACEX_LOGO_GIF
import ru.myitschool.nasa_bootcamp.utils.loadImage


class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        DimensionsUtil.dpToPx(requireContext(), 15).let {
            DimensionsUtil.setMargins(
                binding.back,
                it,
                DimensionsUtil.getStatusBarHeight(resources) + it,
                it,
                it
            )
        }

        binding.historyCard.setOnClickListener {
            val action = ExploreFragmentDirections.actionExploreFragmentToHistoryFragment()
            navController.navigate(action)
        }

        binding.roadsterCard.setOnClickListener {
            val action = ExploreFragmentDirections.actionExploreFragmentToRoadsterFragment()
            navController.navigate(action)
        }

        binding.capsulesCard.setOnClickListener {
            val action = ExploreFragmentDirections.actionExploreFragmentToCapsulesFragment()
            navController.navigate(action)
        }

        binding.coresCard.setOnClickListener {
            val action = ExploreFragmentDirections.actionExploreFragmentToCoresFragment()
            navController.navigate(action)
        }

        binding.dragonsCard.setOnClickListener {
            val action = ExploreFragmentDirections.actionExploreFragmentToDragonsFragment()
            navController.navigate(action)
        }

        binding.launchCard.setOnClickListener {
            val action = ExploreFragmentDirections.actionExploreFragmentToLaunchLandFragment()
            navController.navigate(action)
        }
        binding.back.setOnClickListener{
            navController.navigateUp()
        }
        loadImage(requireContext(), SPACEX_LOGO_GIF, binding.starmanGif)
    }
}