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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.databinding.FragmentUpcomingEventsBinding
import ru.myitschool.nasa_bootcamp.utils.DimensionsUtil

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recylcerUpcoming.setHasFixedSize(false)
        binding.recylcerUpcoming.layoutManager = GridLayoutManager(context, 1)

        launchesViewModel.getViewModelScope().launch {
            launchesViewModel.getUpcomingLaunches()
        }
        activity?.main_loading?.startLoadingAnimation()

        launchesViewModel.getUpcomingList().observe(viewLifecycleOwner) {

            upcomingEventsAdapter =
                UpcomingRecylcerAdapter(
                    requireContext(),
                    launchesViewModel.getUpcomingList().value!!
                )

            binding.recylcerUpcoming.adapter = upcomingEventsAdapter
            activity?.main_loading?.stopLoadingAnimation()
        }

        DimensionsUtil.dpToPx(requireContext(), 10).let {
            DimensionsUtil.setMargins(
                binding.upcomingDescription,
                it,
                DimensionsUtil.getStatusBarHeight(resources) + it,
                it,
                0
            )
        }
    }

    /*
        Plan a notification:

        val temp = NotificationCentre()
        temp.scheduleNotification(requireContext(), "Notification!", "Parse text", "2021-08-02-21-19-00")
    */
}