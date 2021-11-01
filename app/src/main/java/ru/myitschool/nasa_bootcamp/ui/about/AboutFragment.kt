package ru.myitschool.nasa_bootcamp.ui.about

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.FragmentAboutBinding
import ru.myitschool.nasa_bootcamp.utils.ANIMATED_DEEP_SPACE
import ru.myitschool.nasa_bootcamp.utils.loadImageCircle


/*
 * @author Danil Khairulin
 */
@AndroidEntryPoint
class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AboutFragmentViewModel by viewModels<AboutFragmentViewModelImpl>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        //println(isNetworkAvailable(context))
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadImageCircle(requireContext(), ANIMATED_DEEP_SPACE, binding.logoGif)
        setupDevelopersData()
    }

    private fun setupDevelopersData() {
        with(binding) {
            YanaMail.setOnClickListener {
                copyToClipBoard(YANA_MAIL, binding.YanaGladkikh.text.toString())
            }
            YanaVk.setOnClickListener {
                openLink(YANA_VK)
            }
            YanaGit.setOnClickListener {
                openLink(YANA_GIT)
            }

            DenisVk.setOnClickListener {
                openLink(DENIS_VK)
            }
            DenisMail.setOnClickListener {
                copyToClipBoard(DENIS_MAIL, binding.DenisBarov.text.toString())
            }
            DenisGit.setOnClickListener {
                openLink(DENIS_GIT)
            }

            vladVk.setOnClickListener {
                openLink(VLAD_VK)
            }
            vladGit.setOnClickListener {
                openLink(VLAD_GIT)
            }
            vladMail.setOnClickListener {
                copyToClipBoard(VLAD_MAIL, binding.VladimirKrylov.text.toString())
            }

            samuilVk.setOnClickListener {
                openLink(SAMUIL_VK)
            }
            samuilGit.setOnClickListener {
                openLink(SAMUIL_GIT)
            }
            samuilMail.setOnClickListener {
                copyToClipBoard(SAMUIL_MAIL, binding.samuilNalisin.text.toString())
            }

            danilVk.setOnClickListener {
                openLink(DANIL_VK)
            }
            danilGit.setOnClickListener {
                openLink(DANIL_GIT)
            }
            danilMail.setOnClickListener {
                copyToClipBoard(DANIL_MAIL, binding.danilkhairulin.text.toString())
            }
        }
    }

    private fun openLink(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }

    private fun copyToClipBoard(text: String, name: String) {
        val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("email", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(requireContext(), getString(R.string.copiedToClipboard, name), Toast.LENGTH_SHORT).show();
    }

    companion object Links {
        const val YANA_VK = "https://vk.com/yanaglad12"
        const val YANA_MAIL = "monsterglad12@gmail.com"
        const val YANA_GIT = "https://github.com/YanaGlad"

        const val DENIS_VK = "https://vk.com/brightos"
        const val DENIS_MAIL = "dbarov3@gmail.com"
        const val DENIS_GIT = "https://github.com/BrightOS"

        const val VLAD_VK = "https://vk.com/krylov800"
        const val VLAD_MAIL = "abubakirov04@mail.ru"
        const val VLAD_GIT = "https://github.com/ThreadJava800"

        const val SAMUIL_VK = "https://vk.com/korefu"
        const val SAMUIL_MAIL = "semviel22@gmail.com"
        const val SAMUIL_GIT = "https://github.com/korefu"

        const val DANIL_VK = "https://vk.com/ytowka137"
        const val DANIL_MAIL = "donil.0304@yandex.ru"
        const val DANIL_GIT = "https://github.com/ytowka"
    }
}