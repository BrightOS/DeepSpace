package ru.myitschool.nasa_bootcamp.ui.spacex.explore.cores

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.databinding.FragmentCoresBinding
import ru.myitschool.nasa_bootcamp.utils.DimensionsUtil

@AndroidEntryPoint
class CoresFragment : Fragment() {

    private val coresViewModel: CoresViewModel by viewModels<CoresViewModelImpl>()
    private var _binding: FragmentCoresBinding? = null
    private val binding get() = _binding!!
    private lateinit var coresAdapter: CoresAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoresBinding.inflate(inflater, container, false)

        DimensionsUtil.dpToPx(requireContext(), 5).let {
            DimensionsUtil.setMargins(
                binding.toolBar,
                it,
                DimensionsUtil.getStatusBarHeight(resources) + it,
                it,
                it
            )
        }

        binding.coresRecycle.setHasFixedSize(true)
        binding.coresRecycle.layoutManager = GridLayoutManager(context, 1)

        coresViewModel.getViewModelScope().launch {
            coresViewModel.getCores()
        }

        coresViewModel.getCoresList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            coresAdapter =
                CoresAdapter(
                    requireContext(),
                    coresViewModel.getCoresList().value!!
                )
            binding.coresRecycle.adapter = coresAdapter
        })

        return binding.root
    }

}