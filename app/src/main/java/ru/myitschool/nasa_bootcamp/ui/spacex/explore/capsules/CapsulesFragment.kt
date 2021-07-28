package ru.myitschool.nasa_bootcamp.ui.spacex.explore.capsules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R

@AndroidEntryPoint
class CapsulesFragment : Fragment() {
    private val capsulesViewModel: CapsulesViewModel by viewModels<CapsulesViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        capsulesViewModel.getViewModelScope().launch {
            capsulesViewModel.getCapsules()
        }

        capsulesViewModel.getCapsulesList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {

        })

        return inflater.inflate(R.layout.fragment_capsules, container, false)
    }

}