package ru.myitschool.deepspace.ui.upcoming_events

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.myitschool.deepspace.databinding.UpcomingItemBinding

class UpcomingRecyclerHolder(
    val binding: UpcomingItemBinding
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
