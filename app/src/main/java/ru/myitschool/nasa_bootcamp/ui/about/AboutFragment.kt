package ru.myitschool.nasa_bootcamp.ui.about

import android.R.attr
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.data.model.ContentWithLikesAndComments
import ru.myitschool.nasa_bootcamp.data.model.NotificationModel
import ru.myitschool.nasa_bootcamp.data.model.UpcomingLaunchModel
import ru.myitschool.nasa_bootcamp.data.repository.FirebaseRepositoryImpl
import ru.myitschool.nasa_bootcamp.databinding.FragmentAboutBinding
import ru.myitschool.nasa_bootcamp.utils.ANIMATED_DEEP_SPACE
import ru.myitschool.nasa_bootcamp.utils.NotificationCentre
import ru.myitschool.nasa_bootcamp.utils.loadImage
import ru.myitschool.nasa_bootcamp.utils.loadImageCircle
import android.R.attr.label

import android.content.ClipData
import android.content.ClipboardManager
import android.widget.Toast
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.getSystemService
import ru.myitschool.nasa_bootcamp.R


@AndroidEntryPoint
class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val viewModel: AboutFragmentViewModel by viewModels<AboutFragmentViewModelImpl>()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //println(isNetworkAvailable(context))
        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        loadImageCircle(requireContext(), ANIMATED_DEEP_SPACE, binding.logoGif)

        binding.YanaMail.setOnClickListener {
            copyToClipBoard(yanaMail, binding.YanaGladkikh.text.toString())
        }
        binding.YanaVk.setOnClickListener {
            openLink(yanaVk)
        }
        binding.YanaGit.setOnClickListener {
            openLink(yanaGit)
        }

        binding.DenisVk.setOnClickListener {
            openLink(denisVk)
        }
        binding.DenisMail.setOnClickListener {
            copyToClipBoard(denisMail, binding.DenisBarov.text.toString() )
        }
        binding.DenisGit.setOnClickListener {
            openLink(denisGit)
        }

        binding.vladVk.setOnClickListener {
            openLink(vladVk)
        }
        binding.vladGit.setOnClickListener {
            openLink(vladGit)
        }
        binding.vladMail.setOnClickListener {
            copyToClipBoard(vladMail, binding.VladimirKrylov.text.toString())
        }

        binding.samuilVk.setOnClickListener {
            openLink(samuilVk)
        }
        binding.samuilGit.setOnClickListener {
            openLink(samuilGit)
        }
        binding.samuilMail.setOnClickListener {
            copyToClipBoard(samuilMail,binding.samuilNalisin.text.toString())
        }

        binding.danilVk.setOnClickListener {
            openLink(danilVk)
        }
        binding.danilGit.setOnClickListener {
            openLink(danilGit)
        }
        binding.danilMail.setOnClickListener {
            copyToClipBoard(danilMail,binding.danilkhairulin.text.toString())
        }

        return binding.root
    }

    private fun openLink(link: String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }
    private fun copyToClipBoard(text: String, name: String){
        val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("email", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(requireContext(),getString(R.string.copiedToClipboard,name),Toast.LENGTH_SHORT).show();
    }

    companion object Links{
        const val yanaVk = "https://vk.com/yanaglad12"
        const val yanaMail = "monsterglad12@gmail.com"
        const val yanaGit = "https://github.com/YanaGlad"

        const val denisVk = "https://vk.com/brightos"
        const val denisMail = "dbarov3@gmail.com"
        const val denisGit = "https://github.com/BrightOS"

        const val vladVk = "https://vk.com/krylov800"
        const val vladMail = "abubakirov04@mail.ru"
        const val vladGit = "https://github.com/ThreadJava800"

        const val samuilVk = "https://vk.com/korefu"
        const val samuilMail = "semviel22@gmail.com"
        const val samuilGit = "https://github.com/korefu"

        const val danilVk = "https://vk.com/ytowka137"
        const val danilMail = "donil.0304@yandex.ru"
        const val danilGit = "https://github.com/ytowka"
    }
}