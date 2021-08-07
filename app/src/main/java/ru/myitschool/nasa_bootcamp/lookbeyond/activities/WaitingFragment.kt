package ru.myitschool.nasa_bootcamp.lookbeyond.activities

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWaitingBinding.inflate(inflater, container, false)

        val navController = findNavController()

        GlobalScope.launch {
            delay(1000)
            val action = WaitingFragmentDirections.actionWaitingFragmentToSpaceNavigatorActivity()
            MainScope().launch {
                navController.navigate(action)
            }
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.waitingLoading.startLoadingAnimation()
    }

}