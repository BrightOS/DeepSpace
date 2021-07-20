package ru.myitschool.nasa_bootcamp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.firstkotlinapp.model.models.ApodModel
import com.example.kotlintraining.api.Api
import com.example.kotlintraining.api.Instance

class ApodViewModel : ViewModel() {
    public lateinit var apodModel: MutableLiveData<ApodModel>

    suspend fun loadApod() {
        val response = Instance.getInstance("https://api.nasa.gov/").create(Api::class.java)
            .getAstronomyImageOfTheDay2()

        if (response.isSuccessful()) {
            if (response.body() != null) {
                apodModel.value = response.body()!!.createApodModel()
            }
        }
    }
}