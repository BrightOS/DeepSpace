package ru.myitschool.nasa_bootcamp.ui.spacex

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.core.view.ViewCompat.animate
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_explore.*
import ru.myitschool.nasa_bootcamp.databinding.FragmentExploreBinding
import ru.myitschool.nasa_bootcamp.ui.animation.animateIt
import ru.myitschool.nasa_bootcamp.utils.STARMAN_GIF_LINK
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
        loadImage(requireContext(), STARMAN_GIF_LINK, binding.starmanGif)
    }
}