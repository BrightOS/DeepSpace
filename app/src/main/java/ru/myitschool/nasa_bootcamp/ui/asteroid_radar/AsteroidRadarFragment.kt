package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.databinding.FragmentAsteroidRadarBinding

@AndroidEntryPoint
class AsteroidRadarFragment : Fragment() {
    private var _binding: FragmentAsteroidRadarBinding? = null
    private val asteroidViewModel: AsteroidRadarViewModel by viewModels<AsteroidRadarViewModelImpl>()

    private lateinit var asteroidAdapter: AsteroidAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAsteroidRadarBinding.inflate(inflater, container, false)

        asteroidViewModel.getViewModelScope().launch {
            asteroidViewModel.getAsteroidList()
        }

        binding.asteroidList.setHasFixedSize(true)
        binding.asteroidList.layoutManager = GridLayoutManager(context, 1)


        asteroidViewModel.getAsteroidListViewModel().observe(viewLifecycleOwner, Observer {
            asteroidAdapter =
                AsteroidAdapter(
                    requireContext(),
                    asteroidViewModel.getAsteroidListViewModel().value!!
                )
            binding.asteroidList.adapter = asteroidAdapter
        })


        return binding.root
    }
}