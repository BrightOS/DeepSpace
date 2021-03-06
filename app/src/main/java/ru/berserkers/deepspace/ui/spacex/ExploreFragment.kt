package ru.berserkers.deepspace.ui.spacex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import ru.berserkers.deepspace.databinding.FragmentExploreBinding
import ru.berserkers.deepspace.utils.DimensionsUtil
import ru.berserkers.deepspace.utils.SPACEX_LOGO_GIF
import ru.berserkers.deepspace.utils.loadImage

/*
 * @author Yana Glad
 */
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

        with(binding) {
            DimensionsUtil.dpToPx(requireContext(), 15).let {
                DimensionsUtil.setMargins(
                   view =  back,
                   left =  it,
                   top = DimensionsUtil.getStatusBarHeight(resources) + it,
                   right = it,
                   bottom = it,
                )
            }
            setupNavigation(findNavController())
            loadImage(requireContext(), SPACEX_LOGO_GIF, starmanGif)
        }
    }

    private fun setOnClickListener(card: MaterialCardView, func: ()-> Unit){
        card.setOnClickListener {
            func()
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
