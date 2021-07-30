package ru.myitschool.nasa_bootcamp.ui.spacex.explore.dragons

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.databinding.FragmentDragonsBinding

@AndroidEntryPoint
class DragonsFragment : Fragment() {
    private val dragonsViewModel: DragonsViewModel by viewModels<DragonsViewModelImpl>()

    private var _binding: FragmentDragonsBinding? = null
    private val binding get() = _binding!!
    private lateinit var dragonsAdapter: DragonsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDragonsBinding.inflate(inflater, container, false)

        binding.dragonsRecycle.setHasFixedSize(true)
        binding.dragonsRecycle.layoutManager = GridLayoutManager(context, 1)

        dragonsViewModel.getViewModelScope().launch {
            dragonsViewModel.getDragons()
        }

        dragonsViewModel.getDragonsList().observe(viewLifecycleOwner, {
            dragonsAdapter =
                DragonsAdapter(
                    requireContext(),
                    dragonsViewModel.getDragonsList().value!!
                )
            binding.dragonsRecycle.adapter = dragonsAdapter
        })

        return binding.root
    }

}