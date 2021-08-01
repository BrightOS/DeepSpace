package ru.myitschool.nasa_bootcamp.ui.upcoming_events

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.myitschool.nasa_bootcamp.databinding.UpcomingItemBinding
import ru.myitschool.nasa_bootcamp.utils.ErrorHandler
import javax.sql.DataSource

class UpcomingRecyclerHolder (
    val binding: UpcomingItemBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    var requestListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any,
            target: Target<Drawable>,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable,
            model: Any,
            target: Target<Drawable>,
            dataSource: com.bumptech.glide.load.DataSource,
            isFirstResource: Boolean
        ): Boolean {
            // viewModel.error.value = ErrorHandler.SUCCESS
            return false
        }
    }
}