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
import ru.myitschool.nasa_bootcamp.data.model.PostItem
import ru.myitschool.nasa_bootcamp.data.model.PostView
import ru.myitschool.nasa_bootcamp.databinding.FragmentViewPostBinding
import ru.myitschool.nasa_bootcamp.utils.Data

class ViewPostFragment : Fragment() {
    private var _binding: FragmentViewPostBinding? = null
    private val viewModel: ViewAllPostViewModel by viewModels<ViewAllPostViewModelImpl>()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: ViewPostAdapter

    private var id: String? = null
    private var title: String? = null
    private var author: String? = null
    private var dateCreated: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString("id")
            title = it.getString("title")
            author = it.getString("author")
            dateCreated = it.getString("dateCreated")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPostBinding.inflate(inflater, container, false)

        binding.fbPostTitle.text = title
        binding.fbPostUsername.text = author
        binding.fbPostCreated.text = dateCreated
        viewModel.getViewModelScope().launch {
            viewModel.getAdditionalData(id!!).observe(viewLifecycleOwner) {
                when(it) {
                    is Data.Ok -> {
                        adapter = ViewPostAdapter(requireContext(), it.data, id!!, viewModel, viewLifecycleOwner)
                        binding.fbPostRecycler.layoutManager = LinearLayoutManager(requireContext())
                        binding.fbPostRecycler.adapter = adapter
                    }

                    is Data.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        /*

         */

        return binding.root
    }
}