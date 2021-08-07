package ru.myitschool.nasa_bootcamp.ui.user_create_post

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.data.fb_general.MFirebaseUser
import ru.myitschool.nasa_bootcamp.data.dto.firebase.ImagePost
import ru.myitschool.nasa_bootcamp.data.dto.firebase.Post
import ru.myitschool.nasa_bootcamp.data.dto.firebase.PostItem
import ru.myitschool.nasa_bootcamp.data.dto.firebase.TextPost
import ru.myitschool.nasa_bootcamp.databinding.FragmentCreatePostBinding
import ru.myitschool.nasa_bootcamp.utils.Data

@AndroidEntryPoint
class CreatePostFragment : Fragment() {
    private val PICK_IMAGE_REQUEST = 71

    private var _binding: FragmentCreatePostBinding? = null
    private val viewModel: CreatePostViewModel by viewModels<CreatePostViewModelImpl>()

    private val binding get() = _binding!!

    private var recyclerList: ArrayList<PostItem> = ArrayList()
    private lateinit var adapter: CreatePostRecyclerAdapter
    private lateinit var interactionResult: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePostBinding.inflate(inflater, container, false)
        interactionResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    recyclerList.add(
                        PostItem(
                            CreatePostRecyclerAdapter.IMAGE,
                            null,
                            MediaStore.Images.Media.getBitmap(
                                activity?.contentResolver,
                                it.data?.data
                            ),
                            it.data?.data
                        )
                    )
                    adapter.notifyItemInserted(adapter.itemCount)
                }
            }
        adapter = CreatePostRecyclerAdapter(requireContext(), recyclerList)
        adapter.data = recyclerList
        binding.createPostRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.createPostRecycler.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!MFirebaseUser().isUserAuthenticated()) {
            Toast.makeText(requireContext(), "You are not authenticated, you want be able to post!", Toast.LENGTH_LONG).show()
            // TODO: navigate from here to login activity
        }

        binding.addText.setOnClickListener {
            recyclerList.add(PostItem(CreatePostRecyclerAdapter.TEXT, null, null, null))
            adapter.notifyItemInserted(adapter.itemCount)
        }

        binding.addImg.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            intent.putExtra("requestCode", PICK_IMAGE_REQUEST)
            interactionResult.launch(Intent.createChooser(intent, "Select photo"))
        }

        binding.postButton.setOnClickListener {
            pushPost()
        }
    }

    private fun pushPost() {
        val post = Post()
        post.title = binding.postTitle.text.toString()  // TODO: check if not null
        viewModel.getLastPostId().observe(viewLifecycleOwner) { id ->
            when (id) {
                is Data.Ok -> {
                    val postId = id.data
                    var picCount: Int = 1
                    var allCount: Int = 1  // count of text and picture views

                    for (postItem in adapter.getList()) {
                        if (postItem.type == CreatePostRecyclerAdapter.IMAGE) {
                            viewModel.uploadImage(postId, picCount, postItem.imagePath!!)
                                .observe(viewLifecycleOwner) {
                                    when (it) {
                                        is Data.Ok -> {
                                        }   // if picture uploaded successfully
                                        is Data.Error -> {
                                            Toast.makeText(
                                                requireContext(),
                                                it.message,
                                                Toast.LENGTH_LONG
                                            )
                                                .show()
                                        }
                                    }
                                }
                            post.data?.add(ImagePost(allCount, postItem.type, picCount.toString()))
                            picCount++
                            allCount++
                        } else {
                            if (postItem.text != null) {
                                post.data?.add(TextPost(allCount, postItem.type, postItem.text!!))
                                allCount++
                            }
                        }
                    }

                    lifecycleScope.launchWhenStarted {
                        when (val it =  viewModel.createPost(post, postId)) {
                            is Data.Ok -> {
                                Toast.makeText(requireContext(), "Success!", Toast.LENGTH_LONG)
                                    .show()
                                // TODO: add progress bar and move to another activity
                            }
                            is Data.Error -> {
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                }

                is Data.Error -> {
                    // if failed to get last id (due to internet connection or other reasons)
                    Toast.makeText(requireContext(), id.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}
