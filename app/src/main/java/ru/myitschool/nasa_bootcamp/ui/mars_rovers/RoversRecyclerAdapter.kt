package ru.myitschool.nasa_bootcamp.ui.mars_rovers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialSharedAxis
import ru.myitschool.nasa_bootcamp.data.model.RoverModel
import ru.myitschool.nasa_bootcamp.databinding.RoverItemBinding
import ru.myitschool.nasa_bootcamp.utils.loadImage
import java.util.ArrayList

class RoverRecyclerAdapter internal constructor(
    var context: Context,
    roverModels: ArrayList<RoverModel>,
    val navController: NavController
) :
    RecyclerView.Adapter<RoverViewHolder>() {
    var roverModels: ArrayList<RoverModel>

    internal interface OnRoverClickListener {
        fun onRoverClick(roverModel: RoverModel, position: Int)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoverViewHolder {
        return RoverViewHolder(
            RoverItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RoverViewHolder, position: Int) {
        val roverModel: RoverModel = roverModels[position]
        loadImage(context, roverModel.img_src, holder.binding.recycleRoverItemImg)

        Glide.with(holder.itemView)
            .load(roverModel.img_src)
            .into(holder.binding.recycleRoverItemImg)

        holder.binding.infoLayout.setOnClickListener {
            val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Z, true)
            holder.binding.cardRovers.let {
                TransitionManager.beginDelayedTransition(it as ViewGroup, sharedAxis)
            }

            holder.binding.infoLayout.visibility = View.GONE
            holder.binding.infoButton.visibility = View.VISIBLE
            holder.binding.wallpaperButton.visibility = View.VISIBLE
        }

        holder.binding.infoButton.setOnClickListener {
            if (holder.binding.infoLayout.visibility == View.GONE) {
                val sharedAxis = MaterialSharedAxis(MaterialSharedAxis.Z, false)
                holder.binding.cardRovers.let {
                    TransitionManager.beginDelayedTransition(it as ViewGroup, sharedAxis)
                }

                holder.binding.infoLayout.visibility = View.VISIBLE
                holder.binding.infoButton.visibility = View.GONE
                holder.binding.wallpaperButton.visibility = View.GONE
            }
        }

        holder.binding.wallpaperButton.setOnClickListener {
            navController.navigate(
                MarsRoversFragmentDirections.actionMarsRoversFragmentToReviewFragment(
                    roverModel.img_src
                )
            )
        }

        holder.binding.roverName.text = roverModel.rover.name
        holder.binding.landingDate.text = roverModel.earth_date
        holder.binding.launchingDate.text = roverModel.rover.launch_date
        holder.binding.status.text = roverModel.rover.status
        holder.binding.camera.text =
            if (roverModel.camera.fullname != null)
                roverModel.camera.fullname
            else
                roverModel.camera.name
    }

    override fun getItemCount(): Int {
        return roverModels.size
    }

    fun update(modelList: ArrayList<RoverModel>) {
        roverModels = modelList
        notifyDataSetChanged()
    }

    init {
        this.roverModels = roverModels
    }
}