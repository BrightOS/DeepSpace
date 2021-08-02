package ru.myitschool.nasa_bootcamp.ui.view_user_posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.data.model.Post
import ru.myitschool.nasa_bootcamp.databinding.FragmentAllUserPostBinding
import ru.myitschool.nasa_bootcamp.utils.Data

class ViewAllPostFragment : Fragment() {
    private var _binding: FragmentAllUserPostBinding? = null
    private val viewModel: ViewAllPostViewModel by viewModels<ViewAllPostViewModelImpl>()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private var recyclerList: ArrayList<Post> = ArrayList()
    private lateinit var adapter: ViewAllPostAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllUserPostBinding.inflate(inflater, container, false)
        adapter = ViewAllPostAdapter(requireContext(), findNavController(), recyclerList)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getViewModelScope().launch {
            viewModel.getAllPosts().observe(viewLifecycleOwner){
                when(it) {
                    is Data.Ok -> {
                        recyclerList = it.data
                        adapter.data = recyclerList
                        binding.allPostRecycler.layoutManager = LinearLayoutManager(requireContext())
                        binding.allPostRecycler.adapter = adapter
                    }
                    is Data.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        println(it.message)
                    }
                }
            }
        }
    }
}