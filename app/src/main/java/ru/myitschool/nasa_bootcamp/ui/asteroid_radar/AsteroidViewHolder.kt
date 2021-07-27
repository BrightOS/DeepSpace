package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.myitschool.nasa_bootcamp.databinding.AsteroidItemBinding
import ru.myitschool.nasa_bootcamp.databinding.LaunchItemBinding

class AsteroidViewHolder (val binding: AsteroidItemBinding,
                              private val context: Context
) : RecyclerView.ViewHolder(binding.root)
