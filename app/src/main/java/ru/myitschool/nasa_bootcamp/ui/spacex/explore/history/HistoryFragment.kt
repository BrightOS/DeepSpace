package ru.myitschool.nasa_bootcamp.ui.spacex.explore.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.databinding.FragmentHistoryBinding
import ru.myitschool.nasa_bootcamp.utils.DimensionsUtil

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var historyAdapter: HistoryAdapter
    private val historyViewModel: HistoryViewModel by viewModels<HistoryViewModelImpl>()

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            DimensionsUtil.dpToPx(requireContext(), 5).let {
                DimensionsUtil.setMargins(
                    toolBar,
                    it,
                    DimensionsUtil.getStatusBarHeight(resources) + it,
                    it,
                    it
                )
            }

            historyViewModel.getViewModelScope().launch {
                historyViewModel.getHistory()
            }

            setupRecycler()
        }
    }

    private fun FragmentHistoryBinding.setupRecycler() {
        historyRecycle.setHasFixedSize(true)
        historyViewModel.getHistoryList().apply {
            observe(viewLifecycleOwner, {
                historyAdapter =
                    HistoryAdapter(
                        value!!
                    )
                historyRecycle.adapter = historyAdapter
            })
        }
    }

}