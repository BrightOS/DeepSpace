package ru.myitschool.nasa_bootcamp.ui.upcoming_events

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.databinding.FragmentUpcomingEventsBinding

@AndroidEntryPoint
class UpcomingEventsFragment : Fragment() {
    private var _binding: FragmentUpcomingEventsBinding? = null
    private val launchesViewModel: UpcomingEventsViewModel by viewModels<UpcomingEventsViewModelImpl>()
    private lateinit var upcomingEventsAdapter: UpcomingRecylcerAdapter

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentUpcomingEventsBinding.inflate(inflater, container, false)

        binding.recylcerUpcoming.setHasFixedSize(true)
        binding.recylcerUpcoming.layoutManager = GridLayoutManager(context, 1)

        launchesViewModel.getViewModelScope().launch {
            launchesViewModel.getUpcomingLaunches()
        }

        launchesViewModel.getUpcomingList().observe(viewLifecycleOwner) {

            upcomingEventsAdapter =
                UpcomingRecylcerAdapter(requireContext(), launchesViewModel.getUpcomingList().value!!)

            binding.recylcerUpcoming.adapter = upcomingEventsAdapter
        }

        return binding.root
    }
}