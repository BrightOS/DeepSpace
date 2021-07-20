package ru.myitschool.nasa_bootcamp.ui.nasa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.myitschool.nasa_bootcamp.databinding.FragmentNasaBinding

class NasaFragment : Fragment() {
    private var _binding: FragmentNasaBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNasaBinding.inflate(inflater, container, false)
        return binding.root
    }
}