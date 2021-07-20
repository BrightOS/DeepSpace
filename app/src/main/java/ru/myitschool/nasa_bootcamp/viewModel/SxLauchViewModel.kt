package ru.myitschool.nasa_bootcamp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlintraining.api.Api
import com.example.kotlintraining.api.Instance
import ru.myitschool.nasa_bootcamp.model.models.SxLaunchModel

class SxLauchViewModel : ViewModel() {

    var launchModels: MutableLiveData<ArrayList<SxLaunchModel>> =
        MutableLiveData<ArrayList<SxLaunchModel>>()

    var list: ArrayList<SxLaunchModel> = arrayListOf()

    suspend fun loadLauches(){
        val response = Instance.getInstance("https://api.spacexdata.com/v3/").create(Api::class.java).getLaunches()

        Log.d("LaunchResponseTag", "${response.body()!!.get(0).createLaunchModel().upcoming}  upcoming")

    }
}