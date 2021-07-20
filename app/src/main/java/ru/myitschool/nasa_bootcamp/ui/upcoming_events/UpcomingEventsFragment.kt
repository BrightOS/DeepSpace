package ru.myitschool.nasa_bootcamp.ui.upcoming_events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.myitschool.nasa_bootcamp.databinding.FragmentUpcomingEventsBinding

class UpcomingEventsFragment : Fragment() {
    private var _binding: FragmentUpcomingEventsBinding? = null

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
}