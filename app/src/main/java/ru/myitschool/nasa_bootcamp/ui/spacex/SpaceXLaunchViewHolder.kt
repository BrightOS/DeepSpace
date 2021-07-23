package ru.myitschool.nasa_bootcamp.ui.spacex

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.myitschool.nasa_bootcamp.R

class SpaceXLaunchViewHolder (private val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView

    private val requestListener: RequestListener<Drawable> = object : RequestListener<Drawable> {
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
            dataSource: DataSource,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }
    }

    fun loadImage(url: String?) {
        Glide.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(requestListener)
          //  .placeholder(R.drawable.waiting_background)
            .into(imageView)
    }

    init {
        imageView = itemView.findViewById(R.id.recycle_item_img)
    }
}
