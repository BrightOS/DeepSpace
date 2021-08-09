package ru.myitschool.nasa_bootcamp.ui.contact

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.FragmentContactBinding
import ru.myitschool.nasa_bootcamp.utils.Data

@AndroidEntryPoint
class ContactFragment : Fragment() {
    private var _binding: FragmentContactBinding? = null
    private val viewModel: ContactViewModel by viewModels<ContactViewModelImpl>()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.titleText.doOnTextChanged { _, _, _, _ ->
            binding.titleForFeedback.isErrorEnabled = false
        }
        binding.nameText.doOnTextChanged { _, _, _, _ ->
            binding.nameForFeedback.isErrorEnabled = false
        }
        binding.emailText.doOnTextChanged { _, _, _, _ ->
            binding.emailForFeedback.isErrorEnabled = false
        }
        binding.textText.doOnTextChanged { _, _, _, _ ->
            binding.textForFeedback.isErrorEnabled = false
        }

        binding.sendFeedbackButton.setOnClickListener {
            val title = binding.titleText.text.toString()
            val name = binding.nameText.text.toString()
            val email = binding.emailText.text.toString()
            val comments = binding.textText.text.toString()
            if (title.isEmpty() || name.isEmpty() || email.isEmpty() || comments.isEmpty()) {
                if (title.isEmpty()) {
                    binding.titleForFeedback.error = getString(R.string.emptyField)
                }
                if (name.isEmpty()) {
                    binding.nameForFeedback.error = getString(R.string.emptyField)
                }
                if (email.isEmpty()) {
                    binding.emailForFeedback.error = getString(R.string.emptyField)
                }
                if (comments.isEmpty()) {
                    binding.textForFeedback.error = getString(R.string.emptyField)
                }
            }
            else {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.sendFeedback(title, name, email, comments).observe(viewLifecycleOwner) {
                    when (it) {
                        is Data.Ok -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            findNavController().navigate(R.id.contactToMain)
                            Toast.makeText(requireContext(), getString(R.string.feedback_sent), Toast.LENGTH_LONG).show()
                        }
                        is Data.Error -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }
}