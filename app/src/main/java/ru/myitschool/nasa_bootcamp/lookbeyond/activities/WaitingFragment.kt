package ru.myitschool.nasa_bootcamp.lookbeyond.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.content.ContextWrapper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkPermission
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*
import ru.myitschool.nasa_bootcamp.MainActivity
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.databinding.FragmentWaitingBinding
import ru.myitschool.nasa_bootcamp.databinding.SpaceNavigatorBinding
import ru.myitschool.nasa_bootcamp.ui.spacex.ExploreFragmentDirections


class WaitingFragment : Fragment() {

    private var _binding: FragmentWaitingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @DelicateCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWaitingBinding.inflate(inflater, container, false)

        val navController = findNavController()

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            GlobalScope.launch {
                delay(1000)
                val action =
                    WaitingFragmentDirections.actionWaitingFragmentToSpaceNavigatorActivity()
                MainScope().launch {

                    navController.navigate(action)
                    Toast.makeText(context, "Loading...", Toast.LENGTH_LONG).show()
                }
            }

            return binding.root
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_REQUEST_FINE_LOCATION
            )
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.waitingLoading.startLoadingAnimation()
    }

    @DelicateCoroutinesApi
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_FINE_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TAG", "fine location permission granted")

                    GlobalScope.launch {
                        delay(1000)
                        val action =
                            WaitingFragmentDirections.actionWaitingFragmentToSpaceNavigatorActivity()
                        MainScope().launch {

                            findNavController().navigate(action)
                            Toast.makeText(context, "Loading...", Toast.LENGTH_LONG).show()
                        }
                    }
                } else if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ),
                        PERMISSION_REQUEST_FINE_LOCATION
                    )
                } else {
                    findNavController().popBackStack()
                    Toast.makeText(context, "Necessary permits have not been issued.", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_FINE_LOCATION = 1
    }
}
