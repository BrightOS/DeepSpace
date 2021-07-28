package ru.myitschool.nasa_bootcamp.ui.spacex.explore.cores

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.dto.spaceX.cores.Core
import ru.myitschool.nasa_bootcamp.ui.spacex.explore.capsules.CapsulesViewModel
import ru.myitschool.nasa_bootcamp.ui.spacex.explore.capsules.CapsulesViewModelImpl

@AndroidEntryPoint
class CoresFragment : Fragment() {

    private val coresViewModel: CoresViewModel by viewModels<CoresViewModelImp>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        coresViewModel.getViewModelScope().launch {
            coresViewModel.getCores()
        }

        coresViewModel.getCoresList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {

        })

        return inflater.inflate(R.layout.fragment_cores, container, false)
    }

}