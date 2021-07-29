package ru.myitschool.nasa_bootcamp.ui.spacex.explore.history

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentInfoBinding.inflate(inflater, container, false)

        infoViewModel.getViewModelScope().launch {
            infoViewModel.getHistory()
            infoViewModel.getInfo()
        }

        infoViewModel.getHistoryList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {

        })

        infoViewModel.getInfoLiveData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            binding.addressInfo.text =
                "Address: ${infoViewModel.getInfoLiveData().value!!.headquarters.address}"
            binding.summaryInfo.text = infoViewModel.getInfoLiveData().value!!.summary
            binding.ceoInfo.text = "CEO : ${infoViewModel.getInfoLiveData().value!!.ceo}"
            binding.cooInfo.text = "COO : ${infoViewModel.getInfoLiveData().value!!.coo}"
            binding.ctoInfo.text = "CTO : ${infoViewModel.getInfoLiveData().value!!.cto}"
            binding.cityInfo.text =
                "City : ${infoViewModel.getInfoLiveData().value!!.headquarters.city}"
            binding.ctoPropulsionInfo.text =
                "CTO propulsion : ${infoViewModel.getInfoLiveData().value!!.cto}"
            binding.launchSitesInfo.text =
                "Launch sites : ${infoViewModel.getInfoLiveData().value!!.launch_sites}"
            binding.vehiclesInfo.text =
                "Vehicles : ${infoViewModel.getInfoLiveData().value!!.vehicles}"
            binding.valuationInfo.text =
                "Valuation : ${infoViewModel.getInfoLiveData().value!!.valuation}"
            binding.stateInfo.text =
                "State : ${infoViewModel.getInfoLiveData().value!!.headquarters.state}"
            binding.employeesInfo.text =
                "Employees : ${infoViewModel.getInfoLiveData().value!!.employees}"
        })

        val animation = animateIt {
            animate(binding.iconInfo) animateTo {
                scale(0.8f, 0.8f)
            }

            animate(binding.backgInfo) animateTo {
                height(resources.getDimensionPixelOffset(R.dimen.height_120),
                    horizontalGravity = Gravity.LEFT, verticalGravity = Gravity.TOP)
            }
        }

        binding.scrollInfo.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            val percent = scrollY * 1f / v.maxScrollAmount
            animation.setPercent(percent)
        })

        return binding.root
    }
}
