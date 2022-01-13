package ru.berserkers.deepspace.ui.auth.reg

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import ru.berserkers.deepspace.MainActivity
import ru.berserkers.deepspace.R
import ru.berserkers.deepspace.databinding.FragmentRegBinding
import ru.berserkers.deepspace.utils.Data
import ru.berserkers.deepspace.utils.checkForErrors
import java.io.IOException

/*
 * @author Danil Khairulin
 */
@DelicateCoroutinesApi
@AndroidEntryPoint
class RegFragment : Fragment() {
    companion object {
        private const val PICK_IMAGE_REQUEST = 71
    }

    private var imagePath: Uri? = null

    private val viewModel: RegViewModel by viewModels<RegViewModelImpl>()
    private lateinit var interactionResult: ActivityResultLauncher<Intent>

    private var _binding: FragmentRegBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
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

        with(binding) {

            textName.checkForErrors(textLayoutName)
            textEmail.checkForErrors(textInputLayoutEmail)
            textPassword.checkForErrors(textLayoutPassword)
            textPasswordRepeat.checkForErrors(textLayoutPasswordRepeat)

            loginToAnExistingAccount.setOnClickListener {
                findNavController().popBackStack()
            }

            buttonBack.setOnClickListener {
                findNavController().popBackStack()
            }

            imageView.setOnClickListener {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                intent.putExtra("requestCode", PICK_IMAGE_REQUEST)
                interactionResult.launch(Intent.createChooser(intent, "Select avatar"))
            }

            buttonSend.setOnClickListener {
                if (!loading) {
                    (activity as MainActivity).hideKeyboard()
                    loading = true

                    var fieldsValidation = true

                    if (textName.text.isNullOrBlank()) {
                        fieldsValidation = false
                        textLayoutName.error = getString(R.string.emptyField)
                    }

                    if (textEmail.text.isNullOrBlank()) {
                        fieldsValidation = false
                        textInputLayoutEmail.error = getString(R.string.emptyField)
                    }
                    if (textPassword.text.isNullOrBlank()) {
                        fieldsValidation = false
                        textLayoutPassword.error = getString(R.string.emptyField)
                    }

                    if (textPasswordRepeat.text.toString() != textPassword.text.toString()) {
                        fieldsValidation = false
                        textLayoutPasswordRepeat.error =
                            getString(R.string.passwordRepeatDontMatch)
                    }

                    if (fieldsValidation) {
                        (activity as MainActivity).startLoadingAnimation()

                        val userName = textName.text.toString()
                        val password = textPassword.text.toString()

                        lifecycleScope.launchWhenStarted {
                            when (val data = viewModel.createUser(
                                requireContext(),
                                userName,
                                textEmail.text.toString(),
                                password,
                                imagePath
                            )) {
                                is Data.Ok -> successRegister()
                                is Data.Error -> (activity as MainActivity).showError(data.message)
                            }
                        }
                    } else {
                        loading = false
                    }
                }
            }
        }
    }

    private fun successRegister() {
        (activity as MainActivity).apply {
            stopLoadingAnimation(true)
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
