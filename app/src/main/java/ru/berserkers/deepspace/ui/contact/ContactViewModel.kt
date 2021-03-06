package ru.berserkers.deepspace.ui.contact

import androidx.lifecycle.LiveData
import ru.berserkers.deepspace.utils.Data

/*
 * @author Danil Khairulin
 */
interface ContactViewModel {
    fun sendFeedback(title: String, name: String, email: String, text: String): LiveData<Data<Boolean>>
}
