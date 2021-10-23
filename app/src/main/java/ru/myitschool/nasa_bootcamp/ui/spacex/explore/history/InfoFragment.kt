package ru.myitschool.nasa_bootcamp.ui.spacex.explore.history

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.FragmentInfoBinding
import ru.myitschool.nasa_bootcamp.ui.animation.animateIt

@AndroidEntryPoint
class InfoFragment : Fragment() {
    private val infoViewModel: InfoViewModel by viewModels<InfoViewModelImpl>()

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        infoViewModel.getViewModelScope().launch {
            infoViewModel.getInfo()
        }
        with(binding) {
            infoViewModel.getInfoLiveData().observe(viewLifecycleOwner, {
                initData()
            })
            setupAnimation()
            setupNavigation()
        }
    }

    private fun FragmentInfoBinding.setupNavigation() {
        layHistory.historyGo.setOnClickListener {
            findNavController().navigate(InfoFragmentDirections.actionHistoryFragmentToHistoryFragment2())
        }
    }

    private fun FragmentInfoBinding.initData() {
        infoViewModel.getInfoLiveData().value!!.also {
            addressInfo.text =
                it.headquarters.address
            founderInfo.text = it.founder
            foundedInfo.text = "${it.founded}"
            summaryInfo.text = it.summary
            ceoInfo.text = it.ceo
            cooInfo.text = it.coo
            ctoInfo.text = it.cto
            cityInfo.text =
                it.headquarters.city
            ctoPropulsionInfo.text =
                it.cto
            launchSitesInfo.text =
                "${it.launch_sites}"
            vehiclesInfo.text =
                "${it.vehicles}"
            valuationInfo.text =
                "${it.valuation}"
            stateInfo.text =
                it.headquarters.state
            employeesInfo.text =
                "${it.employees}"
        }
    }

    private fun FragmentInfoBinding.setupAnimation() {
        val animation = animateIt {
            animate(iconInfo) animateTo {
                scale(0.8f, 0.8f)
            }

            animate(backgInfo) animateTo {
                height(
                    resources.getDimensionPixelOffset(R.dimen.height_120),
                    horizontalGravity = Gravity.LEFT, verticalGravity = Gravity.TOP
                )
            }
        }

        scrollInfo.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            val percent = scrollY * 1f / v.maxScrollAmount
            animation.setPercent(percent)
        })
    }
}
