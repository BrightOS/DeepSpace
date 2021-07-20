package ru.myitschool.nasa_bootcamp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.kotlintraining.api.Api
import com.example.kotlintraining.api.Instance

class DragonsViewModel : ViewModel() {

    suspend fun loadDragons(){
        val response = Instance.getInstance("https://api.spacexdata.com/v3/").create(Api::class.java).getDragons()

        Log.d("Tag_Drag", response.body()!!.get(0).createDragonModel().thrusters.get(0).fuel_1)


    }
}