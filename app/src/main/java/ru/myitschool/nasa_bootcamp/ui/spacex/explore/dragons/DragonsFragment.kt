package ru.myitschool.nasa_bootcamp.ui.spacex.explore.dragons

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.ui.spacex.explore.cores.CoresViewModel
import ru.myitschool.nasa_bootcamp.ui.spacex.explore.cores.CoresViewModelImp

@AndroidEntryPoint
class DragonsFragment : Fragment() {
    private val dragonsViewModel: DragonsViewModel by viewModels<DragonsViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        dragonsViewModel.getViewModelScope().launch {
            dragonsViewModel.getDragons()
        }

        dragonsViewModel.getDragonsList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {

        })

        return inflater.inflate(R.layout.fragment_dragons, container, false)
    }

}