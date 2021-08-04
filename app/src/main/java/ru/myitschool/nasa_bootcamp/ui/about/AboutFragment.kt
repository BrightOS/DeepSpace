package ru.myitschool.nasa_bootcamp.ui.about

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.myitschool.nasa_bootcamp.data.model.NotificationModel
import ru.myitschool.nasa_bootcamp.data.model.UpcomingLaunchModel
import ru.myitschool.nasa_bootcamp.databinding.FragmentAboutBinding
import ru.myitschool.nasa_bootcamp.utils.NotificationCentre


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
        // println(isNetworkAvailable(context))
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        // NotificationCentre().saveNotification(requireContext(), NotificationModel("New", "Test"))
        val notifModel = NotificationCentre().scheduleNotification(requireContext(), "New", "Hehe", "2021-08-04-19-50-00", UpcomingLaunchModel("Test", null, -1, true))
        val notifModel2 = NotificationCentre().scheduleNotification(requireContext(), "New", "Best", "2021-08-04-19-51-00", UpcomingLaunchModel("Test", null, -1, true))
        NotificationCentre().cancelNotification(requireContext(), notifModel)
        return binding.root
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}