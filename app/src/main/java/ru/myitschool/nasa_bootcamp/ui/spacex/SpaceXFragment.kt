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
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.FragmentSpacexBinding
import ru.myitschool.nasa_bootcamp.ui.animation.animateIt

@AndroidEntryPoint
class SpaceXFragment : Fragment() {

    private val launchesViewModel: SpaceXViewModelImpl by viewModels()
    private lateinit var spaceXLaunchAdapter: SpaceXLaunchAdapter

    private var _binding: FragmentSpacexBinding? = null
    private val binding get() = _binding!!


    internal var h: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launchesViewModel.viewModelScope.launch {
            launchesViewModel.getSpaceXLaunches()
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpacexBinding.inflate(inflater, container, false)

        h = resources.getDimensionPixelOffset(R.dimen.height)

        val animation = animateIt {
            animate(binding.spaceXLogo) animateInto {
                topOfItsParent(marginDp = 15f)
                leftOfItsParent(marginDp = 10f)
                scale(0.8f, 0.8f)
            }

            animate(binding.launches) animateInto {
                rightOf(binding.spaceXLogo, marginDp = 1f)
                sameCenterVerticalAs(binding.spaceXLogo)
            }

            animate(binding.explore) animateInto {
                rightOfItsParent(marginDp = 20f)
                sameCenterVerticalAs(binding.spaceXLogo)
            }

            animate(binding.background) animateInto {
                height(h, horizontalGravity = Gravity.LEFT, verticalGravity = Gravity.TOP)
            }
        }


        binding.scrollview.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            val percent = scrollY * 1f / v.maxScrollAmount
            animation.setPercent(percent)
        })

        val navController = findNavController()


        binding.explore.setOnClickListener(View.OnClickListener {
            navController.navigate(R.id.exploreFragment)
        })

        binding.launchesRecycle.setHasFixedSize(true)
        binding.launchesRecycle.layoutManager = GridLayoutManager(context, 1)

        launchesViewModel.viewModelScope.launch {
            launchesViewModel.getSpaceXLaunches()
        }


        launchesViewModel.launchesModelsList.observe(viewLifecycleOwner, Observer {
            Log.d("SpaceX_Fragment_TAG", "Something changed in view model!")
            spaceXLaunchAdapter = SpaceXLaunchAdapter(requireContext(), launchesViewModel.launchesModelsList.value!!)
            binding.launchesRecycle.adapter = spaceXLaunchAdapter
        })


        return binding.root
    }
}