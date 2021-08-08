package ru.myitschool.nasa_bootcamp.ui.spacex.explore.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.databinding.FragmentHistoryBinding
import ru.myitschool.nasa_bootcamp.ui.spacex.SpaceXViewModelImpl
import ru.myitschool.nasa_bootcamp.ui.spacex.explore.launchland.LandAdapter
import ru.myitschool.nasa_bootcamp.utils.DimensionsUtil

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var historyAdapter: HistoryAdapter
    private val historyViewModel: HistoryViewModel by viewModels<HistoryViewModelImpl>()

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)


        DimensionsUtil.dpToPx(requireContext(), 5).let {
            DimensionsUtil.setMargins(
                binding.toolBar,
                it,
                DimensionsUtil.getStatusBarHeight(resources) + it,
                it,
                it
            )
        }


        binding.historyRecycle.setHasFixedSize(true)
        binding.historyRecycle.layoutManager = GridLayoutManager(context, 1)

        historyViewModel.getViewModelScope().launch {
            historyViewModel.getHistory()
        }


        historyViewModel.getHistoryList().observe(viewLifecycleOwner, {
            historyAdapter =
                HistoryAdapter(
                    requireContext(),
                    historyViewModel.getHistoryList().value!!,
                 )
            binding.historyRecycle.adapter = historyAdapter
        })



        return binding.root
    }


}