package ru.berserkers.deepspace.ui.spacex

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import ru.berserkers.deepspace.MainActivity
import ru.berserkers.deepspace.R
import ru.berserkers.deepspace.databinding.FragmentSpacexBinding
import ru.berserkers.deepspace.ui.animation.animateIt
import ru.berserkers.deepspace.utils.Data
import ru.berserkers.deepspace.utils.DimensionsUtil

/*
 * @author Yana Glad
 */
@AndroidEntryPoint
class SpaceXFragment : Fragment() {

    private lateinit var spaceXLaunchAdapter: SpaceXLaunchAdapter
    private val launchesViewModel: SpaceXViewModel by viewModels<SpaceXViewModelImpl>()

    private var _binding: FragmentSpacexBinding? = null
    private val binding get() = _binding!!

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSpacexBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycle()

        with(binding) {
            DimensionsUtil.dpToPx(requireContext(), 5).let {
                DimensionsUtil.setMargins(
                    view = toolBar,
                    left = it,
                    top = DimensionsUtil.getStatusBarHeight(resources) + it,
                    right = it,
                    bottom = it
                )
            }

            observeSpaceXLaunchers()

            reconnect.setOnClickListener {
                reconnectProgressBar.visibility = VISIBLE
                observeSpaceXLaunchers()
            }

            setupAnimations()

            explore.setOnClickListener {
                val action = SpaceXFragmentDirections.actionSpaceXFragmentToExploreFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun observeSpaceXLaunchers() {
        launchesViewModel.getSpaceXLaunches().observe(viewLifecycleOwner) { data ->
            with(binding) {
                when (data) {
                    is Data.Ok -> {
                        noInternet.visibility = GONE
                        reconnect.visibility = GONE
                        reconnectProgressBar.visibility = GONE
                        loadProgressbar.visibility = GONE
                        textNoInternet.visibility = GONE

                        spaceXLaunchAdapter.submitList(data.data)
                        (activity as MainActivity).stopLoadingAnimation(false)
                    }
                    is Data.Error -> {
                        if (data.message == "noInternet") {
                            (activity as MainActivity).stopLoadingAnimation(false)

                            noInternet.visibility = VISIBLE
                            reconnect.visibility = VISIBLE
                            reconnectProgressBar.visibility = GONE
                            loadProgressbar.visibility = GONE
                            textNoInternet.visibility = VISIBLE


                            Toast.makeText(
                                requireContext(),
                                "no internet connection",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is Data.Local -> {
                        spaceXLaunchAdapter.submitList(data.data)
                        (activity as MainActivity).stopLoadingAnimation(false)
                    }
                    Data.Loading -> {

                    }
                }
            }
        }
    }

    private fun setupRecycle() {
        spaceXLaunchAdapter = SpaceXLaunchAdapter()
        binding.launchesRecycle.adapter = spaceXLaunchAdapter
    }

    @SuppressLint("RtlHardcoded")
    private fun setupAnimations() {
        val animation = animateIt {
            animate(binding.spaceXLogo) animateTo {
                topOfItsParent(marginDp = 35f)
                leftOfItsParent(marginDp = 10f)
                scale(0.8f, 0.8f)
            }

            animate(binding.explore) animateTo {
                rightOfItsParent(marginDp = 20f)
                sameCenterVerticalAs(binding.spaceXLogo)
            }

            animate(binding.background) animateTo {
                height(resources.getDimensionPixelOffset(R.dimen.height), horizontalGravity = Gravity.LEFT, verticalGravity = Gravity.TOP)
            }
        }

        binding.launchesRecycle.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            val percent = scrollY * 1f
            animation.setPercent(percent)
        }
    }

    override fun onPause() {
        (activity as MainActivity).stopLoadingAnimation(false)
        super.onPause()
    }
}
