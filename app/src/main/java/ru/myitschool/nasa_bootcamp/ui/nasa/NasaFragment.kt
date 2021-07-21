package ru.myitschool.nasa_bootcamp.ui.nasa

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.PagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import ru.myitschool.nasa_bootcamp.databinding.FragmentNasaBinding
import ru.myitschool.nasa_bootcamp.ui.nasa.pages.NasaPagerAdapter
import ru.myitschool.nasa_bootcamp.utils.TAG

@AndroidEntryPoint
class NasaFragment : Fragment() {
    private var _binding: FragmentNasaBinding? = null
    private val viewModel: NasaViewModel by viewModels<NasaViewModelImpl>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNasaBinding.inflate(inflater, container, false)

        val tab = binding.tabs
        val pager = binding.nasaViewPager
        val pageAdapter : PagerAdapter = NasaPagerAdapter(binding.root.context,fragmentManager)
        pager.setAdapter(pageAdapter)
        tab.setupWithViewPager(pager)

        return binding.root
    }
}