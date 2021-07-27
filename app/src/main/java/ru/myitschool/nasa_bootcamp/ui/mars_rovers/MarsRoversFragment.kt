package ru.myitschool.nasa_bootcamp.ui.mars_rovers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.myitschool.nasa_bootcamp.databinding.FragmentMarsRoversBinding

@AndroidEntryPoint
class MarsRoversFragment : Fragment() {
    private var _binding: FragmentMarsRoversBinding? = null
    private val viewModel: MarsRoversViewModel by viewModels<MarsRoversViewModelImpl>()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsRoversBinding.inflate(inflater, container, false)



        return binding.root
    }
}