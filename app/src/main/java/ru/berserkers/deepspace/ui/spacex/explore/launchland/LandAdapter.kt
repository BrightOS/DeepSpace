package ru.berserkers.deepspace.ui.spacex.explore.launchland

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import ru.berserkers.deepspace.data.model.LandPadModel
import ru.berserkers.deepspace.databinding.LandPadItemBinding
import ru.berserkers.deepspace.utils.capitalize
import java.util.*

class LandAdapter internal constructor(
    landModels: ArrayList<LandPadModel>,
    val navController: NavController
) :
    RecyclerView.Adapter<LandHolder>() {
    private var landModels: ArrayList<LandPadModel>

    internal interface OnLandPadClickListener {
        fun onLandPadClick(landModel: LandPadModel, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandHolder {
        return LandHolder(LandPadItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: LandHolder, position: Int) {
        val landPadModel: LandPadModel = landModels[position]

        with(holder.binding) {
            landPadModel.also {
                nameLand.text = it.full_name
                locationLand.text =
                    "${it.location.name}, ${it.location.region}"
                attempedSuccessfulLand.text =
                    "${it.attempted_landings} / ${it.successful_landings}"
                statusLand.text = capitalize(it.status)
                landingTypeLand.text = it.landing_type
                descriptionLand.text = it.details
            }
        }
        val onLandPadClickListener = object : OnLandPadClickListener {
            override fun onLandPadClick(landModel: LandPadModel, position: Int) {
                val action = LaunchLandFragmentDirections.actionToLandMap(
                    landModel.location.longitude.toFloat(),
                    landModel.location.latitude.toFloat(),
                    landModel.full_name,
                    landModel.details
                )

                navController.navigate(action)
            }
        }

        holder.itemView.setOnClickListener {
            onLandPadClickListener.onLandPadClick(
                landModels[position],
                position
            )
        }

    }

    override fun getItemCount(): Int {
        return landModels.size
    }

    fun update(modelList: ArrayList<LandPadModel>) {
        landModels = modelList
        notifyDataSetChanged()
    }

    init {
        this.landModels = landModels
    }
}
