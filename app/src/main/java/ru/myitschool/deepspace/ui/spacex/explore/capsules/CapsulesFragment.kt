package ru.myitschool.deepspace.ui.spacex.explore.capsules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import ru.myitschool.deepspace.databinding.FragmentCapsulesBinding
import ru.myitschool.deepspace.utils.DimensionsUtil
import ru.myitschool.deepspace.utils.Status

/*
 * @author Yana Glad
 */
@DelicateCoroutinesApi
@AndroidEntryPoint
class CapsulesFragment : Fragment() {
    private val capsulesViewModel: CapsulesViewModel by viewModels<CapsulesViewModelImpl>()
    private var _binding: FragmentCapsulesBinding? = null
    private val binding get() = _binding!!

    private lateinit var capsulesAdapter: CapsulesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCapsulesBinding.inflate(inflater, container, false)

        with(binding) {
            spaceLoading.startLoadingAnimation()

            DimensionsUtil.dpToPx(requireContext(), 5).let {
                DimensionsUtil.setMargins(
                    toolBar,
                    it,
                    DimensionsUtil.getStatusBarHeight(resources) + it,
                    it,
                    it
                )
            }

            capsulesViewModel.apply {
                getViewModelScope().launch {
                    getCapsules()
                }
            }

            capsulesRecycler.setHasFixedSize(true)
            capsulesViewModel.getCapsulesList()
                .observe(viewLifecycleOwner, {
                    capsulesAdapter =
                        CapsulesAdapter(
                            capsulesViewModel.getCapsulesList().value!!
                        )
                    capsulesRecycler.adapter = capsulesAdapter
                    spaceLoading.stopLoadingAnimation()
                })

            capsulesViewModel.apply {
                getStatus().observe(viewLifecycleOwner, {
                    if (getStatus().value == Status.ERROR) {
                        capsulesRecycler.visibility = View.GONE
                        noInternet.visibility = View.VISIBLE
                    } else {
                        capsulesRecycler.visibility = View.VISIBLE
                        noInternet.visibility = View.GONE
                    }
                })
            }
        }

        return binding.root
    }

}