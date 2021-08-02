package ru.myitschool.nasa_bootcamp.ui.spacex

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_asteroid_radar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.xml.sax.ErrorHandler
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.databinding.FragmentSpacexBinding
import ru.myitschool.nasa_bootcamp.ui.animation.animateIt
import ru.myitschool.nasa_bootcamp.utils.Data
import ru.myitschool.nasa_bootcamp.utils.Status

@AndroidEntryPoint
class SpaceXFragment : Fragment() {

    private lateinit var spaceXLaunchAdapter: SpaceXLaunchAdapter
    private val launchesViewModel: SpaceXViewModel by viewModels<SpaceXViewModelImpl>()

    private var _binding: FragmentSpacexBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpacexBinding.inflate(inflater, container, false)
        spaceXLaunchAdapter = SpaceXLaunchAdapter()
        binding.launchesRecycle.adapter = spaceXLaunchAdapter

        binding.loadProgressbar.visibility = View.VISIBLE
        launchesViewModel.getSpaceXLaunches().observe(viewLifecycleOwner) { data ->
            binding.loadProgressbar.visibility = View.GONE
            when (data){
                is Data.Ok ->{
                    spaceXLaunchAdapter.submitList(data.data)
                }
                is Data.Error -> {
                }
                is Data.Local -> {
                    spaceXLaunchAdapter.submitList(data.data)
                }
                Data.Loading -> {

                }
            }

        }

        launchesViewModel.getErrorHandler().observe(viewLifecycleOwner) { error ->
            if (error == Status.ERROR) {
                Log.d("LAUNCH_NOT_LOADED_TAG", "No internet connection")
                binding.launchesRecycle.visibility = View.GONE
                binding.errorIcon.visibility = View.VISIBLE
                binding.explore.getBackground().setColorFilter(
                    resources.getColor(R.color.disabled_button),
                    PorterDuff.Mode.SRC_ATOP
                );

            } else if ((error == Status.LOADING)) {
                binding.loadProgressbar.visibility = View.VISIBLE
                binding.launchesRecycle.visibility = View.GONE
                binding.errorIcon.visibility = View.GONE
                binding.explore.getBackground().setColorFilter(
                    resources.getColor(R.color.disabled_button),
                    PorterDuff.Mode.SRC_ATOP
                );
            } else {
                binding.launchesRecycle.visibility = View.VISIBLE
                binding.loadProgressbar.visibility = View.GONE
                binding.explore.getBackground().setColorFilter(
                    resources.getColor(R.color.enabled_button),
                    PorterDuff.Mode.SRC_ATOP
                );
            }

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

            binding.scrollview.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
                val percent = scrollY * 1f / v.maxScrollAmount
                animation.setPercent(percent)
            })

            val navController = findNavController()


            binding.explore.setOnClickListener {
                val action = SpaceXFragmentDirections.actionSpaceXFragmentToExploreFragment()
                navController.navigate(action)
            }

//        binding.launchesRecycle.setHasFixedSize(true)
            binding.launchesRecycle.layoutManager = GridLayoutManager(context, 1)

        }

        return binding.root

    }
}