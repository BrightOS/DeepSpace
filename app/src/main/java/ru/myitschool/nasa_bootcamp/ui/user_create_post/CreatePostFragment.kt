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
import androidx.recyclerview.widget.LinearLayoutManager
import ru.myitschool.nasa_bootcamp.data.model.PostItem
import ru.myitschool.nasa_bootcamp.databinding.FragmentCreatePostBinding

class CreatePostFragment : Fragment() {
    private val PICK_IMAGE_REQUEST = 71

    private var _binding: FragmentCreatePostBinding? = null
    private val viewModel: CreatePostViewModel by viewModels<CreatePostViewModelImpl>()

    private val binding get() = _binding!!

    private val recyclerList: ArrayList<PostItem> = ArrayList()
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
                            )
                        )
                    )
                    setAdapter()
                }
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addText.setOnClickListener {
            recyclerList.add(PostItem(CreatePostRecyclerAdapter.TEXT, null, null))
            setAdapter()
        }

        binding.addImg.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            intent.putExtra("requestCode", PICK_IMAGE_REQUEST)
            interactionResult.launch(Intent.createChooser(intent, "Select photo"))
        }
    }

    private fun setAdapter() {
        val adapter = CreatePostRecyclerAdapter(requireContext(), recyclerList)
        binding.createPostRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.createPostRecycler.adapter = adapter
    }
}
