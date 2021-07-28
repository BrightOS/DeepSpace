package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.dto.nasa.asteroids.Asteroid
import ru.myitschool.nasa_bootcamp.data.model.AsteroidModel
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.databinding.AsteroidItemBinding
import ru.myitschool.nasa_bootcamp.ui.animation.animateIt
import ru.myitschool.nasa_bootcamp.ui.spacex.SpaceXLaunchAdapter

import java.util.*
import kotlin.collections.ArrayList

class AsteroidAdapter internal constructor(
    var context: Context,
    var asteroids: List<AsteroidModel>
) :
    RecyclerView.Adapter<AsteroidViewHolder>() {

    internal interface OnAsteroidClickListener {
        fun onAsteroidClick(asteroid: AsteroidModel?, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {

        return AsteroidViewHolder(
            AsteroidItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context
        )
    }


    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid: AsteroidModel = asteroids[position]
        holder.binding.asteroidName.text = asteroid.name
        holder.binding.diametr.text = "Estimated diametr: ${asteroid.estimatedDiameter}"
        when(asteroid.potncialDanger){
            true-> holder.binding.danger.text = "Dangerous :("
            false->holder.binding.danger.text = "Friendly :)"
        }


        val onLaunchClickListener = object :  OnAsteroidClickListener {
            override fun onAsteroidClick(asteroid: AsteroidModel?, position: Int) {
              Log.d("ASTEROID_CLICK_TAG", "Clicked")
            }
        }


        holder.itemView.setOnClickListener({
            onLaunchClickListener.onAsteroidClick(
                asteroids.get(position),
                position
            )
        })
    }

    override fun getItemCount(): Int {
        return asteroids.size
    }

}