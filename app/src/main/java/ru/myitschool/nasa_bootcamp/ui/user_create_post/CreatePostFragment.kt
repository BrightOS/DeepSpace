package ru.myitschool.nasa_bootcamp.ui.user_create_post

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.data.model.ImagePost
import ru.myitschool.nasa_bootcamp.data.model.Post
import ru.myitschool.nasa_bootcamp.data.model.PostItem
import ru.myitschool.nasa_bootcamp.data.model.TextPost
import ru.myitschool.nasa_bootcamp.databinding.FragmentCreatePostBinding

class CreatePostFragment : Fragment() {
    private val PICK_IMAGE_REQUEST = 71

    private var _binding: FragmentCreatePostBinding? = null
    private val viewModel: CreatePostViewModelImpl = CreatePostViewModelImpl()

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
                    recyclerList = adapter.getList()
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
                    setAdapter()
                }
            }
        adapter = CreatePostRecyclerAdapter(requireContext(), recyclerList)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addText.setOnClickListener {
            recyclerList = adapter.getList()
            recyclerList.add(PostItem(CreatePostRecyclerAdapter.TEXT, null, null, null))
            setAdapter()
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

    private fun setAdapter() {
        adapter.data = recyclerList
        binding.createPostRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.createPostRecycler.adapter = adapter
    }

    private fun pushPost() {
        val post = Post()
        post.title = binding.postTitle.text.toString()
        val postId: String = viewModel.getLastPostId()
        var picCount: Int = 1
        var allCount: Int = 1
        for (postItem in adapter.getList()) {
            if (postItem.type == CreatePostRecyclerAdapter.IMAGE) {
                viewModel.viewModelScope.launch {
                    viewModel.loadImage(postId, picCount, postItem.imagePath!!)
                }
                post.data.add(ImagePost(allCount, picCount.toString()))
                picCount++
                allCount++
            } else {
                if (postItem.text != null) {
                    post.data.add(TextPost(allCount, postItem.text!!))
                    allCount++
                }
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.createPost(post)
        }
    }
}
