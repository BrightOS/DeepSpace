package ru.myitschool.nasa_bootcamp.ui.spacex.explore.capsules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.xml.sax.ErrorHandler
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.FragmentCapsulesBinding
import ru.myitschool.nasa_bootcamp.databinding.FragmentCoresBinding
import ru.myitschool.nasa_bootcamp.ui.spacex.explore.cores.CoresAdapter
import ru.myitschool.nasa_bootcamp.utils.STARS_ANIMATED_BACKGROUND
import ru.myitschool.nasa_bootcamp.utils.Status
import ru.myitschool.nasa_bootcamp.utils.loadImage

@AndroidEntryPoint
class CapsulesFragment : Fragment() {
    private val capsulesViewModel: CapsulesViewModel by viewModels<CapsulesViewModelImpl>()
    private var _binding: FragmentCapsulesBinding? = null
    private val binding get() = _binding!!

    private lateinit var capsulesAdapter: CapsulesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCapsulesBinding.inflate(inflater, container, false)

        capsulesViewModel.getViewModelScope().launch {
            capsulesViewModel.getCapsules()
        }

        binding.capsulesRecycler.setHasFixedSize(true)
        binding.capsulesRecycler.layoutManager = GridLayoutManager(context, 1)

        capsulesViewModel.getCapsulesList()
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                capsulesAdapter =
                    CapsulesAdapter(
                        requireContext(),
                        capsulesViewModel.getCapsulesList().value!!
                    )
                binding.capsulesRecycler.adapter = capsulesAdapter
            })

        capsulesViewModel.getStatus().observe(viewLifecycleOwner, {
            if(capsulesViewModel.getStatus().value == Status.ERROR) {
                binding.capsulesRecycler.visibility = View.GONE
                binding.noInternet.visibility = View.VISIBLE
            }else{
                binding.capsulesRecycler.visibility = View.VISIBLE
                binding.noInternet.visibility = View.GONE
            }
         })

        return binding.root
    }

}