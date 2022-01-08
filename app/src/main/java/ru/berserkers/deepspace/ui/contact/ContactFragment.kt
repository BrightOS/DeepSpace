package ru.berserkers.deepspace.ui.contact

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
import ru.berserkers.deepspace.R
import ru.berserkers.deepspace.databinding.FragmentContactBinding
import ru.berserkers.deepspace.utils.Data

/*
 * @author Danil Khairulin
 */
@AndroidEntryPoint
class ContactFragment : Fragment() {
    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ContactViewModel by viewModels<ContactViewModelImpl>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            titleText.doOnTextChanged { _, _, _, _ ->
                titleForFeedback.isErrorEnabled = false
            }
            nameText.doOnTextChanged { _, _, _, _ ->
                nameForFeedback.isErrorEnabled = false
            }
            emailText.doOnTextChanged { _, _, _, _ ->
                emailForFeedback.isErrorEnabled = false
            }
            textText.doOnTextChanged { _, _, _, _ ->
                textForFeedback.isErrorEnabled = false
            }

            sendFeedbackButton.setOnClickListener {
                val title = titleText.text.toString()
                val name = nameText.text.toString()
                val email = emailText.text.toString()
                val comments = textText.text.toString()
                if (title.isEmpty() || name.isEmpty() || email.isEmpty() || comments.isEmpty()) {
                    if (title.isEmpty()) {
                        titleForFeedback.error = getString(R.string.emptyField)
                    }
                    if (name.isEmpty()) {
                        nameForFeedback.error = getString(R.string.emptyField)
                    }
                    if (email.isEmpty()) {
                        emailForFeedback.error = getString(R.string.emptyField)
                    }
                    if (comments.isEmpty()) {
                        textForFeedback.error = getString(R.string.emptyField)
                    }
                } else {
                    progressBar.visibility = View.VISIBLE
                    viewModel.sendFeedback(title, name, email, comments).observe(viewLifecycleOwner) {
                        when (it) {
                            is Data.Ok -> {
                                progressBar.visibility = View.INVISIBLE
                                findNavController().navigate(R.id.contactToMain)
                                Toast.makeText(requireContext(), getString(R.string.feedback_sent), Toast.LENGTH_LONG)
                                    .show()
                            }
                            is Data.Error -> {
                                progressBar.visibility = View.INVISIBLE
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                            }
                            else -> {
                            }
                        }
                    }
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }
}
