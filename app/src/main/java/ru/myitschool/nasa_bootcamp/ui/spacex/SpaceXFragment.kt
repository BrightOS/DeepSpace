package ru.myitschool.nasa_bootcamp.ui.spacex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.myitschool.nasa_bootcamp.databinding.FragmentSpacexBinding

class SpaceXFragment : Fragment() {
    private var _binding: FragmentSpacexBinding? = null
    private val viewModel: SpaceXViewModel by viewModels<SpaceXViewModelImpl>()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpacexBinding.inflate(inflater, container, false)
        return binding.root
    }
}