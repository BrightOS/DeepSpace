package ru.myitschool.nasa_bootcamp.ui.contact

import androidx.lifecycle.LiveData
import ru.myitschool.nasa_bootcamp.utils.Data

interface ContactViewModel {
    fun sendFeedback(title: String, name: String, email: String, text: String): LiveData<Data<Boolean>>
}