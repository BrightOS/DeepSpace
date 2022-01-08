package ru.berserkers.deepspace.ui.spacex.explore.cores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import ru.berserkers.deepspace.databinding.FragmentCoresBinding
import ru.berserkers.deepspace.utils.DimensionsUtil

@DelicateCoroutinesApi
@AndroidEntryPoint
class CoresFragment : Fragment() {

    private val coresViewModel: CoresViewModel by viewModels<CoresViewModelImpl>()
    private var _binding: FragmentCoresBinding? = null
    private val binding get() = _binding!!
    private lateinit var coresAdapter: CoresAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            binding.spaceLoading.startLoadingAnimation()
            DimensionsUtil.dpToPx(requireContext(), 5).let {
                DimensionsUtil.setMargins(
                    toolBar,
                    it,
                    DimensionsUtil.getStatusBarHeight(resources) + it,
                    it,
                    it
                )
            }

            coresRecycle.setHasFixedSize(true)

            coresViewModel.getViewModelScope().launch {
                coresViewModel.getCores()
            }

            coresViewModel.getCoresList().apply {
                observe(viewLifecycleOwner, {
                    coresAdapter =
                        CoresAdapter(
                            value!!
                        )
                    coresRecycle.adapter = coresAdapter
                    binding.spaceLoading.stopLoadingAnimation()
                })
            }
        }
    }

}