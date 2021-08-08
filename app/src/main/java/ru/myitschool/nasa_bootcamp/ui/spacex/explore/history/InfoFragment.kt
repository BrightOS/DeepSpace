package ru.myitschool.nasa_bootcamp.ui.spacex.explore.history

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.FragmentInfoBinding
import ru.myitschool.nasa_bootcamp.ui.animation.animateIt
import ru.myitschool.nasa_bootcamp.ui.spacex.ExploreFragmentDirections

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
            infoViewModel.getInfo()
        }

        infoViewModel.getInfoLiveData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            binding.addressInfo.text =
                infoViewModel.getInfoLiveData().value!!.headquarters.address
            binding.founderInfo.text = infoViewModel.getInfoLiveData().value!!.founder
            binding.foundedInfo.text = "${infoViewModel.getInfoLiveData().value!!.founded}"
            binding.summaryInfo.text = infoViewModel.getInfoLiveData().value!!.summary
            binding.ceoInfo.text = infoViewModel.getInfoLiveData().value!!.ceo
            binding.cooInfo.text = infoViewModel.getInfoLiveData().value!!.coo
            binding.ctoInfo.text = infoViewModel.getInfoLiveData().value!!.cto
            binding.cityInfo.text =
                infoViewModel.getInfoLiveData().value!!.headquarters.city
            binding.ctoPropulsionInfo.text =
                infoViewModel.getInfoLiveData().value!!.cto
            binding.launchSitesInfo.text =
                "${infoViewModel.getInfoLiveData().value!!.launch_sites}"
            binding.vehiclesInfo.text =
                "${infoViewModel.getInfoLiveData().value!!.vehicles}"
            binding.valuationInfo.text =
                "${infoViewModel.getInfoLiveData().value!!.valuation}"
            binding.stateInfo.text =
                infoViewModel.getInfoLiveData().value!!.headquarters.state
            binding.employeesInfo.text =
                "${infoViewModel.getInfoLiveData().value!!.employees}"
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

        val navController = findNavController()
        binding.layHistory.historyGo.setOnClickListener {
            val action = InfoFragmentDirections.actionHistoryFragmentToHistoryFragment2()
            navController.navigate(action)
        }

        return binding.root
    }
}
