package ru.myitschool.nasa_bootcamp.ui.spacex

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.FragmentSpacexBinding
import ru.myitschool.nasa_bootcamp.ui.animation.animateIt
import ru.myitschool.nasa_bootcamp.ui.asteroid_radar.AsteroidRadarViewModel
import ru.myitschool.nasa_bootcamp.ui.asteroid_radar.AsteroidRadarViewModelImpl

@AndroidEntryPoint
class SpaceXFragment : Fragment() {

    private lateinit var spaceXLaunchAdapter: SpaceXLaunchAdapter
    private val launchesViewModel: SpaceXViewModel by viewModels<SpaceXViewModelImpl>()

    private var _binding: FragmentSpacexBinding? = null
    private val binding get() = _binding!!

    private lateinit var onLaunchClick: SpaceXLaunchAdapter.OnLaunchClickListener


    internal var h: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launchesViewModel.getViewModelScope().launch {
            launchesViewModel.getSpaceXLaunches()
        }

//        onLaunchClick = object : SpaceXLaunchAdapter.OnLaunchClickListener {
//            override fun onLaunchClick(launch: SxLaunchModel?, position: Int) {
//                Log.d("LAUNCH_CLICK_TAG", "Clicked at $position")
//            }
//        }
    }


        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentSpacexBinding.inflate(inflater, container, false)

            h = resources.getDimensionPixelOffset(R.dimen.height)

            val animation = animateIt {
                animate(binding.spaceXLogo) animateTo {
                    topOfItsParent(marginDp = 15f)
                    leftOfItsParent(marginDp = 10f)
                    scale(0.8f, 0.8f)
                }

                animate(binding.launches) animateTo {
                    rightOf(binding.spaceXLogo, marginDp = 1f)
                    sameCenterVerticalAs(binding.spaceXLogo)
                }

                animate(binding.explore) animateTo {
                    rightOfItsParent(marginDp = 20f)
                    sameCenterVerticalAs(binding.spaceXLogo)
                }

                animate(binding.background) animateTo {
                    height(h, horizontalGravity = Gravity.LEFT, verticalGravity = Gravity.TOP)
                }
            }


            binding.scrollview.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                val percent = scrollY * 1f / v.maxScrollAmount
                animation.setPercent(percent)
            })

            val navController = findNavController()


            binding.explore.setOnClickListener {
                val action = SpaceXFragmentDirections.actionSpaceXFragmentToExploreFragment()

                navController.navigate(action)
            }

            binding.launchesRecycle.setHasFixedSize(true)
            binding.launchesRecycle.layoutManager = GridLayoutManager(context, 1)

            launchesViewModel.getViewModelScope().launch {
                launchesViewModel.getSpaceXLaunches()
            }



            launchesViewModel.getLaunchesList().observe(viewLifecycleOwner, Observer {
                Log.d("SpaceX_Fragment_TAG", "Something changed in view model!")
                spaceXLaunchAdapter =
                    SpaceXLaunchAdapter(
                        requireContext(),
                        launchesViewModel.getLaunchesList().value!!
                    )
                binding.launchesRecycle.adapter = spaceXLaunchAdapter
            })


            return binding.root
        }
    }