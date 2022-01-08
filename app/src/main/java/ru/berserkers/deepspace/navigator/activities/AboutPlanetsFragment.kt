package ru.berserkers.deepspace.navigator.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.berserkers.deepspace.databinding.FragmentAboutPlanetsBinding
import ru.berserkers.deepspace.navigator.activities.planetsadapter.PlanetsAdapter
import ru.berserkers.deepspace.navigator.rendertype.Planet


class AboutPlanetsFragment : Fragment() {
    private var _binding: FragmentAboutPlanetsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PlanetsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAboutPlanetsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PlanetsAdapter(object : PlanetsAdapter.OnPlanetClickListener {
            override fun onPlanetClick(planet: Planet, position: Int) {

            }
        })
        adapter.submitList(listOf(
            Planet.Pluto,
            Planet.Neptune,
            Planet.Uranus,
            Planet.Saturn,
            Planet.Jupiter,
            Planet.Mars,
            Planet.Sun,
            Planet.Mercury,
            Planet.Venus,
            Planet.Moon,
            Planet.ISS
        ))
        binding.planetsRecycle.adapter = adapter
    }
}