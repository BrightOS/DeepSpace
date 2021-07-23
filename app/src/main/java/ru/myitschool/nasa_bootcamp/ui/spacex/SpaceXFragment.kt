package ru.myitschool.nasa_bootcamp.ui.spacex

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.FragmentSpacexBinding
import ru.myitschool.nasa_bootcamp.ui.animation.animateIt

@AndroidEntryPoint
class SpaceXFragment : Fragment() {
    private var _binding: FragmentSpacexBinding? = null
    private val viewModel: SpaceXViewModelImpl by viewModels()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!


    internal var h: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.viewModelScope.launch {
            viewModel.getSpaceXLaunches()
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
                topOfItsParent(marginDp = 1f)
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

        binding.explore.setOnClickListener(View.OnClickListener {
            
        })

        return binding.root
    }
}