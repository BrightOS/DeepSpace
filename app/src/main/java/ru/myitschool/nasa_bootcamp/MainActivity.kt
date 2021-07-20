package ru.myitschool.nasa_bootcamp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.viewmodels.FirebaseViewModel

class MainActivity : AppCompatActivity() {
    private var isUserRegistered: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.send_feedback).setOnClickListener {
            val email = findViewById<EditText>(R.id.email_input).text.toString()
            val password =
                findViewById<EditText>(R.id.pass_input).text.toString()  //TODO: пароль должен быть более 6 символов, нужно сделать проверку

            val firebaseViewModel = FirebaseViewModel()
            firebaseViewModel.viewModelScope.launch {
                // firebaseViewModel.SignOutUser()
                firebaseViewModel.AuthenticateUser(email, password)
                isUserRegistered = firebaseViewModel.isSuccess
                if (isUserRegistered) {
                    Toast.makeText(applicationContext, "Success!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, firebaseViewModel.error, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}