package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.myitschool.nasa_bootcamp.databinding.FragmentAsteroidRadarBinding

@AndroidEntryPoint
class AsteroidRadarFragment : Fragment() {
    private var _binding: FragmentAsteroidRadarBinding? = null
    private val viewModel: AsteroidRadarViewModel by viewModels<AsteroidRadarViewModelImpl>()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAsteroidRadarBinding.inflate(inflater, container, false)
        return binding.root
    }
}