package ru.myitschool.nasa_bootcamp.ui.auth.reg

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.FragmentRegBinding
import ru.myitschool.nasa_bootcamp.utils.Data
import ru.myitschool.nasa_bootcamp.utils.Extensions.checkForErrors
import ru.myitschool.nasa_bootcamp.utils.invalidEmail
import ru.myitschool.nasa_bootcamp.utils.userAlreadyRegistered

class RegFragment : Fragment() {
    private lateinit var viewModel: RegViewModel

    private var _binding: FragmentRegBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegBinding.inflate(inflater, container, false)

        //TODO: create view model
        viewModel = ViewModelProvider(this).get(RegViewModelImpl::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var loading = false

        binding.textName.checkForErrors(binding.textLayoutName)
        binding.textEmail.checkForErrors(binding.textInputLayoutEmail)
        binding.textPassword.checkForErrors(binding.textLayoutPassword)
        binding.textPasswordRepeat.checkForErrors(binding.textLayoutPasswordRepeat)

        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.buttonSend.setOnClickListener {
            if(!loading){
                binding.progressBar.visibility = View.VISIBLE
                binding.textError.visibility = View.GONE
                loading = true

                var fieldsValidation = true

                if(binding.textName.text.isNullOrBlank()){
                    fieldsValidation = false
                    binding.textLayoutName.error = getString(R.string.emptyField)
                }

                if(binding.textEmail.text.isNullOrBlank()){
                    fieldsValidation = false
                    binding.textInputLayoutEmail.error = getString(R.string.emptyField)
                }
                if(binding.textPassword.text.isNullOrBlank()){
                    fieldsValidation = false
                    binding.textLayoutPassword.error = getString(R.string.emptyField)
                }

                if(binding.textPasswordRepeat.text.toString() != binding.textPassword.text.toString()){
                    fieldsValidation = false
                    binding.textLayoutPasswordRepeat.error = getString(R.string.passwordRepeatDontMatch)
                }

                if(fieldsValidation){
                    val userName = binding.textName.text.toString()
                    val password = binding.textPassword.text.toString()

                    viewModel.register(userName, binding.textEmail.text.toString(), password).observe(viewLifecycleOwner){
                        binding.progressBar.visibility = View.GONE
                        loading = false

                        when(it){
                            is Data.Ok ->{
                                successRegister()
                            }
                            is Data.Error -> {
                                showError(it.message)
                            }
                        }
                    }
                }else{
                    loading = false
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun successRegister(){
        binding.textError.visibility = View.GONE
        findNavController().navigate(R.id.success_reg)

    }
    private fun showError(error: String){
        when (error) {
            userAlreadyRegistered -> {
                binding.textError.visibility = View.VISIBLE
            }
            invalidEmail -> {
                binding.textInputLayoutEmail.error = getString(R.string.invalidEmail)
            }
            else -> {
                Toast.makeText(requireContext(), "unknown error: $error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}