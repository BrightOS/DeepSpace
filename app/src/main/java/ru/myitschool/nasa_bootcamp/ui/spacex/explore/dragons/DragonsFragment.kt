package ru.myitschool.nasa_bootcamp.ui.spacex.explore.dragons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.databinding.FragmentDragonsBinding
import ru.myitschool.nasa_bootcamp.utils.DRAGONS_GIF
import ru.myitschool.nasa_bootcamp.utils.loadImage

@AndroidEntryPoint
class DragonsFragment : Fragment() {
    private val dragonsViewModel: DragonsViewModel by viewModels<DragonsViewModelImpl>()

    private var _binding: FragmentDragonsBinding? = null
    private val binding get() = _binding!!
    private lateinit var dragonsAdapter: DragonsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDragonsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            dragonsRecycle.setHasFixedSize(true)

            loadImage(requireContext(), DRAGONS_GIF, dragonImage)

            dragonsViewModel.getViewModelScope().launch {
                dragonsViewModel.getDragons()
            }

            dragonsViewModel.getDragonsList().apply {
                observe(viewLifecycleOwner, {
                    dragonsAdapter =
                        DragonsAdapter(
                            requireContext(),
                            value!!
                        )
                    dragonsRecycle.adapter = dragonsAdapter
                })
            }
        }
    }

}