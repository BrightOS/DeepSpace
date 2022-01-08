package ru.myitschool.deepspace.ui.spacex

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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.DelicateCoroutinesApi
import ru.myitschool.deepspace.MainActivity
import ru.myitschool.deepspace.R
import ru.myitschool.deepspace.databinding.FragmentSpacexBinding
import ru.myitschool.deepspace.ui.animation.animateIt
import ru.myitschool.deepspace.utils.Data
import ru.myitschool.deepspace.utils.DimensionsUtil

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
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpacexBinding.inflate(inflater, container, false)
        setupRecycle()

        DimensionsUtil.dpToPx(requireContext(), 5).let {
            DimensionsUtil.setMargins(
                binding.toolBar,
                it,
                DimensionsUtil.getStatusBarHeight(resources) + it,
                it,
                it
            )
        }

        fun observeSpaceXLaunchers() {
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
                            (activity as MainActivity).main_loading?.stopLoadingAnimation()
                        }
                        is Data.Error -> {
                            if (data.message == "noInternet") {
                                (activity as MainActivity).main_loading?.stopLoadingAnimation()

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
                            (activity as MainActivity).main_loading?.stopLoadingAnimation()
                        }
                        Data.Loading -> {

                        }
                    }
                }
            }
        }
        observeSpaceXLaunchers()

        binding.reconnect.setOnClickListener {
            binding.reconnectProgressBar.visibility = VISIBLE

            observeSpaceXLaunchers()
        }

        setupAnimations()

        binding.explore.setOnClickListener {
            val action = SpaceXFragmentDirections.actionSpaceXFragmentToExploreFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun setupRecycle() {
        spaceXLaunchAdapter = SpaceXLaunchAdapter()
        binding.launchesRecycle.adapter = spaceXLaunchAdapter
    }

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
                height(
                    resources.getDimensionPixelOffset(R.dimen.height),
                    horizontalGravity = Gravity.LEFT, verticalGravity = Gravity.TOP
                )
            }
        }

        binding.launchesRecycle.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            val percent = scrollY * 1f
            animation.setPercent(percent)
        }
    }

    override fun onPause() {
        (activity as MainActivity).main_loading?.stopLoadingAnimation()
        super.onPause()
    }
}
