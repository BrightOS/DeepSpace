package ru.myitschool.nasa_bootcamp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.viewModel.SxLauchViewModel

class MainActivity : AppCompatActivity() {
    val sxLauchViewModel: SxLauchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sxLauchViewModel.viewModelScope.launch {
            sxLauchViewModel.loadLauches()
        }
    }
}
