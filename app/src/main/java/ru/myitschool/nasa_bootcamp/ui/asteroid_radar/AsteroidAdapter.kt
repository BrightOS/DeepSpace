package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.AsteroidModel
import ru.myitschool.nasa_bootcamp.databinding.AsteroidItemBinding

class AsteroidAdapter internal constructor(
    var context: Context,
    var asteroids: List<AsteroidModel>,
    var navController: NavController
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
        holder.binding.diametr.text = "Distance from Earth: ${asteroid.distanceFromEarth}"
        when(asteroid.potencialDanger){
            true-> holder.binding.danger.text = "Dangerous :("
            false->holder.binding.danger.text = "Friendly :)"
        }


        val onLaunchClickListener = object :  OnAsteroidClickListener {
            override fun onAsteroidClick(asteroid: AsteroidModel?, position: Int) {
                val bundle =  Bundle();
                asteroid?.let { bundle.putDouble("absolute_magnitude", it.absolute_magnitude) };
                asteroid?.let { bundle.putDouble("estimatedDiameter", it.estimatedDiameter) };
                asteroid?.let { bundle.putDouble("relativeVelocity", it.relativeVelocity) };
                asteroid?.let { bundle.putDouble("distanceFromEarth", it.distanceFromEarth) };
                asteroid?.let { bundle.putBoolean("potencialDanger", it.potencialDanger) };

               /// val action = AsteroidRadarFragmentDirections.actionAsteroidRadarFragmentToAsteroidDetails(bundle)
                navController.navigate(R.id.asteroidDetails, bundle)
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