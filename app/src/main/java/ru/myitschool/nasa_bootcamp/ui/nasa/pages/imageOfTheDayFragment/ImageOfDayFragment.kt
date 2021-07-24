package ru.myitschool.nasa_bootcamp.ui.nasa.pages.imageOfTheDayFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ru.myitschool.nasa_bootcamp.databinding.FragmentNasaBinding
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageOfDayFragment : Fragment() {
    private val viewModel : ImageOfDayViewModelImpl by viewModels()

    private var _binding: FragmentNasaBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNasaBinding.inflate(inflater, container, false)


        return binding.root
    }

    companion object {
        fun getInstance(): ImageOfDayFragment {
            val fragment = ImageOfDayFragment()
            return fragment
        }
    }

}