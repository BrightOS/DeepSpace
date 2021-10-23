package ru.myitschool.nasa_bootcamp.ui.spacex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import ru.myitschool.nasa_bootcamp.databinding.FragmentExploreBinding
import ru.myitschool.nasa_bootcamp.utils.DimensionsUtil
import ru.myitschool.nasa_bootcamp.utils.SPACEX_LOGO_GIF
import ru.myitschool.nasa_bootcamp.utils.loadImage


class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

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

        with(binding) {
            DimensionsUtil.dpToPx(requireContext(), 15).let {
                DimensionsUtil.setMargins(
                    back,
                    it,
                    DimensionsUtil.getStatusBarHeight(resources) + it,
                    it,
                    it
                )
            }
            setupNavigation(navController)
            loadImage(requireContext(), SPACEX_LOGO_GIF, starmanGif)
        }
    }

    private fun FragmentExploreBinding.setupNavigation(navController: NavController) {
        historyCard.setOnClickListener {
            navController.navigate(ExploreFragmentDirections.actionExploreFragmentToHistoryFragment())
        }

        roadsterCard.setOnClickListener {
            navController.navigate(ExploreFragmentDirections.actionExploreFragmentToRoadsterFragment())
        }

        capsulesCard.setOnClickListener {
            navController.navigate(ExploreFragmentDirections.actionExploreFragmentToCapsulesFragment())
        }

        coresCard.setOnClickListener {
            navController.navigate(ExploreFragmentDirections.actionExploreFragmentToCoresFragment())
        }

        dragonsCard.setOnClickListener {
            navController.navigate(ExploreFragmentDirections.actionExploreFragmentToDragonsFragment())
        }

        launchCard.setOnClickListener {
            navController.navigate(ExploreFragmentDirections.actionExploreFragmentToLaunchLandFragment())
        }
        back.setOnClickListener {
            navController.navigateUp()
        }
    }
}