package ru.myitschool.nasa_bootcamp.ui.mars_rovers

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.RoverModel
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.databinding.AsteroidItemBinding
import ru.myitschool.nasa_bootcamp.databinding.RoverItemBinding
import ru.myitschool.nasa_bootcamp.ui.animation.animateIt
import ru.myitschool.nasa_bootcamp.ui.asteroid_radar.AsteroidViewHolder
import ru.myitschool.nasa_bootcamp.ui.spacex.SpaceXLaunchAdapter
import ru.myitschool.nasa_bootcamp.ui.spacex.SpaceXLaunchViewHolder
import ru.myitschool.nasa_bootcamp.utils.loadImage
import java.util.ArrayList

class RoverRecyclerAdapter internal constructor(
    context: Context,
    roverModels: ArrayList<RoverModel>,
    val navController: NavController
) :
    RecyclerView.Adapter<RoverViewHolder>() {
    var context: Context
    var roverModels: ArrayList<RoverModel>

    internal interface OnRoverClickListener {
        fun onRoverClick(roverModel: RoverModel, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoverViewHolder {
        return RoverViewHolder(
            RoverItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context
        )
    }

    override fun onBindViewHolder(holder: RoverViewHolder, position: Int) {
        val roverModel: RoverModel = roverModels[position]
        loadImage(context, roverModel.img_src, holder.binding.recycleRoverItemImg)

        val onRoverClickListener = object : OnRoverClickListener {
            override fun onRoverClick(roverModel: RoverModel, position: Int) {
                val bundle = Bundle();
                Log.d("NAME_TAG", "Name is ${roverModel.rover.name}")
                bundle.putString("name", roverModel.rover.name)
                bundle.putString("landing_date", roverModel.rover.landing_date)
                bundle.putString("launch_date", roverModel.rover.launch_date)
                bundle.putString("status", roverModel.rover.status)
                bundle.putString("camera", roverModel.camera.fullname)

                navController.navigate(R.id.roverDetails, bundle)
            }
        }

        holder.itemView.setOnClickListener({
            onRoverClickListener.onRoverClick(
                roverModel,
                position
            )
        })
    }

    override fun getItemCount(): Int {
        return roverModels.size
    }

    fun update(modelList: ArrayList<RoverModel>) {
        roverModels = modelList
        notifyDataSetChanged()
    }

    init {
        this.context = context
        this.roverModels = roverModels
    }
}