package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.databinding.FragmentAsteroidRadarBinding
import ru.myitschool.nasa_bootcamp.ui.spacex.SpaceXLaunchAdapter
import ru.myitschool.nasa_bootcamp.ui.spacex.SpaceXViewModelImpl

@AndroidEntryPoint
class AsteroidRadarFragment : Fragment() {
    private val asteroidViewModel: AsteroidRadarViewModelImpl by viewModels()

    private var _binding: FragmentAsteroidRadarBinding? = null
   //private val viewModel: AsteroidRadarViewModel by viewModels<AsteroidRadarViewModelImpl>()
   private lateinit var asteroidAdapter: AsteroidAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAsteroidRadarBinding.inflate(inflater, container, false)

        asteroidViewModel.viewModelScope.launch {
            asteroidViewModel.getAsteroidList()
        }

        binding.asteroidList.setHasFixedSize(true)
        binding.asteroidList.layoutManager = GridLayoutManager(context, 1)

        asteroidViewModel.listOfAsteroids.observe(viewLifecycleOwner, Observer {
            Log.d("Asteroid_Fragment_TAG", "Something changed in asteroid view model! ${asteroidViewModel.listOfAsteroids.value!![0].name}")
            asteroidAdapter = AsteroidAdapter(requireContext(), asteroidViewModel.listOfAsteroids.value!!)
            binding.asteroidList.adapter = asteroidAdapter
        })


        return binding.root
    }
}