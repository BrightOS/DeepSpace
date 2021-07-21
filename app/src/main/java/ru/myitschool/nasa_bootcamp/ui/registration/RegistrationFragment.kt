package ru.myitschool.nasa_bootcamp.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.myitschool.nasa_bootcamp.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val viewModel: RegistrationViewModel by viewModels<RegistrationViewModelImpl>()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }
}

/*
    Чтобы войти в существующий аккаунт:
    val firebaseViewModel = FirebaseViewModel()
    firebaseViewModel.viewModelScope.launch
    {
        firebaseViewModel.authenticateUser(email, password)
        // firebaseViewModel.createUser(email, password) // для регистрации
        if (firebaseViewModel.isSuccess) {
            Toast.makeText(applicationContext, "Success!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, firebaseViewModel.error, Toast.LENGTH_SHORT)
                .show()
        }
    }

 */
