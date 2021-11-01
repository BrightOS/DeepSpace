package ru.myitschool.nasa_bootcamp.ui.auth.user_cabinet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.myitschool.nasa_bootcamp.databinding.FragmentContactBinding

/*
 * @author Danil Khairulin
 */
class UserCabinetFragment : Fragment() {
    private var _binding: FragmentContactBinding? = null
    private val viewModel: UserCabinetViewModel by viewModels<UserCabinetViewModelImpl>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }
}