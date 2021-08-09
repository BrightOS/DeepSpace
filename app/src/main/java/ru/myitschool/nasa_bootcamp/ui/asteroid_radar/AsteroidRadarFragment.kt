package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.stickyheader.StickyHeaderLinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.transition.platform.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_asteroid_radar.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.FragmentAsteroidRadarBinding
import ru.myitschool.nasa_bootcamp.utils.*
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class AsteroidRadarFragment : Fragment() {
    private var _binding: FragmentAsteroidRadarBinding? = null
    private val asteroidViewModel: AsteroidRadarViewModel by viewModels<AsteroidRadarViewModelImpl>()

    private lateinit var asteroidController: AsteroidEpoxyController
    private val binding get() = _binding!!

    private var currentTime = getTodayDateFormatted()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAsteroidRadarBinding.inflate(inflater, container, false)

        DimensionsUtil.dpToPx(requireContext(), 5).let {
            DimensionsUtil.setMargins(
                binding.toolBar,
                it,
                DimensionsUtil.getStatusBarHeight(resources) + it,
                it,
                it
            )
        }

        binding.appbar.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                // Initialize the size of the scroll
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }

                // Check if the view is collapsed
                if (scrollRange + verticalOffset == 0) {
                    binding.toolBar.setBackgroundColor(
                        getColorFromAttributes(requireContext(), R.attr.mainBackground)
                    )
                } else {
                    binding.toolBar.setBackgroundColor(
                        ContextCompat.getColor(requireContext(), android.R.color.transparent)
                    )
                }
            }
        })

        if (!this::asteroidController.isInitialized) {
            asteroidController = AsteroidEpoxyController(requireContext())
            activity?.main_loading?.startLoadingAnimation()

            asteroidViewModel.getViewModelScope().launch {
                asteroidViewModel.getAsteroidList()
            }
        }

        val list = ArrayList<Long>()
        for (i in -8..-1)
            list.add(AsteroidDateEpoxyModel_().id(-1).id())

        binding.asteroidList.let {
            it.layoutManager = StickyHeaderLinearLayoutManager(requireContext())
            it.adapter = asteroidController.adapter
        }

        asteroidViewModel.getAsteroidListViewModel().observe(viewLifecycleOwner, {
            GlobalScope.launch {
                asteroidController.setData(
                    asteroidViewModel.getAsteroidListViewModel().value
                )

                MainScope().launch {
                    activity?.main_loading?.stopLoadingAnimation(false)
                }
            }
        })

        return binding.root
    }

    override fun onPause() {
        activity?.main_loading?.stopLoadingAnimation()
        super.onPause()
    }
}