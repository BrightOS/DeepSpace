package ru.myitschool.nasa_bootcamp.ui.mars_rovers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.myitschool.nasa_bootcamp.data.model.RoverModel

class MarsRoversViewModelImpl: ViewModel(), MarsRoversViewModel {
    var roverModels: MutableLiveData<ArrayList<RoverModel>> =
        MutableLiveData<ArrayList<RoverModel>>()
    var list: ArrayList<RoverModel> = arrayListOf()
//
//    suspend fun loadRoverPhotos() {
//        val response =
//
//        if (response.isSuccessful) {
//            if (response.body() != null) {
//                for (r in response.body()!!.rovers) {
//                    Log.d(
//                        "RoverCreatesModel",
//                        "ADDING: ${r?.createRoverModel()!!.id}" + r.createRoverModel().img_src
//                    )
//                    list.add(r.createRoverModel())
//                }
//            }
//        }
//
//        roverModels.value = list
//
//        for (r in roverModels.value!!)
//            Log.d("CHTO", "" + r.id + " " + r.img_src)
//    }
}