package ru.myitschool.nasa_bootcamp.ui.view_user_posts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.dto.firebase.Post
import java.util.*
import kotlin.collections.ArrayList

class ViewAllPostAdapter(private val context: Context, private val navController: NavController, var data: ArrayList<Post>) :
    RecyclerView.Adapter<ViewAllPostAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var postButton: Button? = null
        var usernameTextView: TextView? = null
        var dateTextView: TextView? = null

        init {
            postButton = itemView.findViewById(R.id.toPost)
            usernameTextView = itemView.findViewById(R.id.fbAuthor)
            dateTextView = itemView.findViewById(R.id.fbDateCreated)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.all_user_post_recycler, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.postButton?.text = data[position].title
        holder.postButton?.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", data[position].id)
            bundle.putString("title", data[position].title)
            bundle.putString("author", data[position].author)
            bundle.putString("dateCreated", Date(data[position].dateCreated).toString())
            navController.navigate(R.id.userPost, bundle)
        }
        holder.usernameTextView?.text = data[position].author
        holder.dateTextView?.text = Date(data[position].dateCreated).toString()
    }
}