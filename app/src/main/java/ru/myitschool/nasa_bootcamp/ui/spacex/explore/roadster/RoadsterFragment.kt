package ru.myitschool.nasa_bootcamp.ui.spacex.explore.roadster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.ui.spacex.explore.history.HistoryViewModel
import ru.myitschool.nasa_bootcamp.ui.spacex.explore.history.HistoryViewModelImpl


class RoadsterFragment : Fragment() {
    private val roadsterViewModel: RoadsterViewModel by viewModels<RoadsterViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        roadsterViewModel.getViewModelScope().launch {
            roadsterViewModel.getRoadsterInfo()
        }

        roadsterViewModel.getRoadsterModel().observe(viewLifecycleOwner, androidx.lifecycle.Observer {

        })

        return inflater.inflate(R.layout.fragment_roadster, container, false)
    }

}