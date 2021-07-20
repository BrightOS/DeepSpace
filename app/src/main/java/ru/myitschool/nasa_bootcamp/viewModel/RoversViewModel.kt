package ru.myitschool.nasa_bootcamp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firstkotlinapp.model.models.RoverModel
import com.example.kotlintraining.api.Api
import com.example.kotlintraining.api.Instance

class RoversViewModel  : ViewModel() {

    var roverModels: MutableLiveData<ArrayList<RoverModel>> =
        MutableLiveData<ArrayList<RoverModel>>()
    var list: ArrayList<RoverModel> = arrayListOf()

    suspend fun loadRoverPhotos() {
        val response =
            Instance.getInstance("https://api.nasa.gov/").create(Api::class.java).getRoverPhotos()

        if (response.isSuccessful) {
            if (response.body() != null) {
                for (r in response.body()!!.photos) {
                    list.add(r.createRoverModel())
                }
            }
        }

        roverModels.value = list
    }
}