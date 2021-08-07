package ru.myitschool.nasa_bootcamp.ui.view_user_posts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.dto.firebase.ImagePost
import ru.myitschool.nasa_bootcamp.data.dto.firebase.PostView
import ru.myitschool.nasa_bootcamp.data.dto.firebase.TextPost
import ru.myitschool.nasa_bootcamp.ui.user_create_post.CreatePostRecyclerAdapter
import ru.myitschool.nasa_bootcamp.utils.Data

class ViewPostAdapter(
    private val context: Context,
    private val data: ArrayList<PostView>,
    private val postId: String,
    private val viewModel: ViewAllPostViewModel,
    private val viewLifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.viewText)
        fun bind(position: Int) {
            textView.text = (data[position] as TextPost).text
        }
    }

    private inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.postImage)
        fun bind(position: Int) {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                when (val it = viewModel.downloadImage(postId, (data[position] as ImagePost).imagePath)) {
                    is Data.Ok -> {
                        imageView.setImageBitmap(it.data)
                    }

                    is Data.Error -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == CreatePostRecyclerAdapter.IMAGE) {
            return ImageViewHolder(
                LayoutInflater.from(context).inflate(R.layout.image_for_recycler, parent, false)
            )
        }
        return TextViewHolder(
            LayoutInflater.from(context).inflate(R.layout.text_view_for_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (data[position]._type == CreatePostRecyclerAdapter.IMAGE) {
            (holder as ViewPostAdapter.ImageViewHolder).bind(position)
        } else {
            (holder as ViewPostAdapter.TextViewHolder).bind(position)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return data[position]._type
    }
}