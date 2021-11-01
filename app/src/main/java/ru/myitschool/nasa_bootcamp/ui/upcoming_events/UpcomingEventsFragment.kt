package ru.myitschool.nasa_bootcamp.ui.upcoming_events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.databinding.FragmentUpcomingEventsBinding
import ru.myitschool.nasa_bootcamp.utils.DimensionsUtil

/*
 * @author Danil Khairulin
 */
@AndroidEntryPoint
class UpcomingEventsFragment : Fragment() {
    private var _binding: FragmentUpcomingEventsBinding? = null
    private val binding get() = _binding!!

    private val launchesViewModel: UpcomingEventsViewModel by viewModels<UpcomingEventsViewModelImpl>()
    private lateinit var upcomingEventsAdapter: UpcomingRecylcerAdapter

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
        initView()
        observeData()
    }

    private fun observeData() {
        launchesViewModel.getViewModelScope().launch {
            launchesViewModel.getUpcomingLaunches()
        }

        launchesViewModel.getUpcomingList().observe(viewLifecycleOwner) {

            upcomingEventsAdapter =
                UpcomingRecylcerAdapter(
                    requireContext(),
                    launchesViewModel.getUpcomingList().value!!
                )

            binding.recylcerUpcoming.adapter = upcomingEventsAdapter
            activity?.main_loading?.stopLoadingAnimation()
        }
    }

    private fun initView() {
        activity?.main_loading?.startLoadingAnimation()

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
