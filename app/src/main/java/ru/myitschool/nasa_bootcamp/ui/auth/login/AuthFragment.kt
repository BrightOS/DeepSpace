package ru.myitschool.nasa_bootcamp.ui.auth.login


import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.MainActivity
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.fb_general.MFirebaseUser
import ru.myitschool.nasa_bootcamp.databinding.FragmentAuthBinding
import ru.myitschool.nasa_bootcamp.utils.Data
import ru.myitschool.nasa_bootcamp.utils.wrongCredits

@AndroidEntryPoint
class AuthFragment : Fragment() {
    private var _binding: FragmentAuthBinding? = null
    private val binding: FragmentAuthBinding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels<AuthViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val transition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var loading = false

        binding.textName.doOnTextChanged { _, _, _, _ ->
            binding.textLayoutName.isErrorEnabled = false
        }
        binding.textPassword.doOnTextChanged { _, _, _, _ ->
            binding.textLayoutPassword.isErrorEnabled = false
        }

        binding.buttonLogin.setOnClickListener {
            binding.textWrongCredits.visibility = View.GONE
            if (!loading) {
                val userName = binding.textName.text.toString()
                val password = binding.textPassword.text.toString()

                if (userName.isEmpty() || password.isEmpty()) {
                    if (userName.isEmpty()) {
                        binding.textLayoutName.error = getString(R.string.emptyField)
                    }
                    if (password.isEmpty()) {
                        binding.textLayoutPassword.error = getString(R.string.emptyField)
                    }
                } else {
                    loading = true
                    binding.progressBar.visibility = View.VISIBLE

                    viewModel.getViewModelScope().launch {
                        viewModel.authenticateUser(requireContext(), userName, password).observe(viewLifecycleOwner) {
                            binding.progressBar.visibility = View.GONE
                            loading = false
                            when (it) {
                                is Data.Ok -> {
                                    onSuccessLogin()
                                }
                                is Data.Error -> {
                                    showError(it.message)
                                }
                            }
                        }
                    }
                }
            }
        }

        binding.buttonReg.setOnClickListener {
            findNavController().navigate(AuthFragmentDirections.reg())
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun onSuccessLogin() {
        (activity as MainActivity).changeHeader()
        findNavController().navigate(R.id.success_login)
    }

    private fun showError(error: String) {
        if (error == wrongCredits) {
            binding.textWrongCredits.visibility = View.VISIBLE
            return
        } else {
            Toast.makeText(requireContext(), "unknown error: $error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}