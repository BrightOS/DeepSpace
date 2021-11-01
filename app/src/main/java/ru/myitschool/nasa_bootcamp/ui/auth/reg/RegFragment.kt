package ru.myitschool.nasa_bootcamp.ui.auth.reg

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.MainActivity
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.FragmentRegBinding
import ru.myitschool.nasa_bootcamp.utils.Data
import ru.myitschool.nasa_bootcamp.utils.checkForErrors
import ru.myitschool.nasa_bootcamp.utils.invalidEmail
import ru.myitschool.nasa_bootcamp.utils.userAlreadyRegistered
import java.io.IOException

/*
 * @author Danil Khairulin
 */
@DelicateCoroutinesApi
@AndroidEntryPoint
class RegFragment : Fragment() {
    private val PICK_IMAGE_REQUEST = 71
    private var imagePath: Uri? = null

    private val viewModel: RegViewModel by viewModels<RegViewModelImpl>()
    private lateinit var interactionResult: ActivityResultLauncher<Intent>

    private var _binding: FragmentRegBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegBinding.inflate(inflater, container, false)

        interactionResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                println("In")
                if (it.resultCode == Activity.RESULT_OK) {
                    setImage(it.data)
                }
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var loading = false

        binding.textName.checkForErrors(binding.textLayoutName)
        binding.textEmail.checkForErrors(binding.textInputLayoutEmail)
        binding.textPassword.checkForErrors(binding.textLayoutPassword)
        binding.textPasswordRepeat.checkForErrors(binding.textLayoutPasswordRepeat)

        binding.loginToAnExistingAccount.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.imageView.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            intent.putExtra("requestCode", PICK_IMAGE_REQUEST)
            interactionResult.launch(Intent.createChooser(intent, "Select avatar"))
        }

        binding.buttonSend.setOnClickListener {
            if (!loading) {
                (activity as MainActivity).hideKeyboard()
                loading = true

                var fieldsValidation = true

                if (binding.textName.text.isNullOrBlank()) {
                    fieldsValidation = false
                    binding.textLayoutName.error = getString(R.string.emptyField)
                }

                if (binding.textEmail.text.isNullOrBlank()) {
                    fieldsValidation = false
                    binding.textInputLayoutEmail.error = getString(R.string.emptyField)
                }
                if (binding.textPassword.text.isNullOrBlank()) {
                    fieldsValidation = false
                    binding.textLayoutPassword.error = getString(R.string.emptyField)
                }

                if (binding.textPasswordRepeat.text.toString() != binding.textPassword.text.toString()) {
                    fieldsValidation = false
                    binding.textLayoutPasswordRepeat.error =
                        getString(R.string.passwordRepeatDontMatch)
                }

                if (fieldsValidation) {
                    (activity as MainActivity).main_loading.startLoadingAnimation()

                    val userName = binding.textName.text.toString()
                    val password = binding.textPassword.text.toString()

                    lifecycleScope.launchWhenStarted {
                        when (val it = viewModel.createUser(
                            requireContext(),
                            userName,
                            binding.textEmail.text.toString(),
                            password,
                            imagePath
                        )) {
                            is Data.Ok -> {
                                successRegister()
                            }
                            is Data.Error -> {
                                (activity as MainActivity).main_loading.showError(it.message)
                            }
                        }
                    }
                } else {
                    loading = false
                }
            }
        }
    }

    private fun successRegister() {
        (activity as MainActivity).apply {
            main_loading.stopLoadingAnimation(true)
            changeHeader()
            hideKeyboard()
        }
        findNavController().navigate(R.id.success_reg)

    }

    private fun setImage(data: Intent?) {
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, data?.data)
            binding.imageView.setImageBitmap(bitmap)
            imagePath = data?.data
        } catch (e: IOException) {
            Toast.makeText(
                requireContext(),
                "Please, try another photo! (${e.message})",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}