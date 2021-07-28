package ru.myitschool.nasa_bootcamp.ui.user_create_post

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.Post

class CreatePostRecyclerAdapter(private val context: Context, var data: ArrayList<Post>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val IMAGE = 1
        const val TEXT = 2
    }


    private inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var editText: EditText = itemView.findViewById(R.id.editText)
        fun bind(position: Int) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    data[position].text = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {
                }

            })
        }
    }

    private inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.postImage)
        fun bind(position: Int) {
            imageView.setImageBitmap(data[position].data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (data[position].type == IMAGE) {
            (holder as ImageViewHolder).bind(position)
        } else {
            (holder as TextViewHolder).bind(position)
        }
    }

    override fun getItemCount(): Int = data.size
}