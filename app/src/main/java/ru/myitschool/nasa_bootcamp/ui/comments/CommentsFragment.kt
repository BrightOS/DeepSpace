package ru.myitschool.nasa_bootcamp.ui.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.myitschool.nasa_bootcamp.databinding.FragmentCommentsBinding

class CommentsFragment : Fragment() {
    private var _binding: FragmentCommentsBinding? = null
    private val viewModel: CommentsViewModel by viewModels<CommentsViewModelImpl>()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }
}

/*
    val test = CommentsViewModelImpl()
    test.listenForComments(1)
    test.comments.observe(viewLifecycleOwner, {
        it?.let {
            for (comment in it) {
                println(comment)
            }
            println("end")
        }
    })
    test.listenForLikes(1)
    test.likes.observe(viewLifecycleOwner, {
        it?.let {
            println("Likes: $it")
        }
    })
 */
