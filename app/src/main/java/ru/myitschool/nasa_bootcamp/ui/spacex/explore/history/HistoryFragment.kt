package ru.myitschool.nasa_bootcamp.ui.spacex.explore.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.ui.spacex.explore.dragons.DragonsViewModel
import ru.myitschool.nasa_bootcamp.ui.spacex.explore.dragons.DragonsViewModelImpl

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private val historyViewModel: HistoryViewModel by viewModels<HistoryViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        historyViewModel.getViewModelScope().launch {
            historyViewModel.getHistory()
        }

        historyViewModel.getHistoryList().observe(viewLifecycleOwner, androidx.lifecycle.Observer {

        })

        return inflater.inflate(R.layout.fragment_history, container, false)
    }
}