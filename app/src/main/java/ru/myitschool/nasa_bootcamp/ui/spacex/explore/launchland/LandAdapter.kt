package ru.myitschool.nasa_bootcamp.ui.spacex.explore.launchland

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.LandPadModel
import ru.myitschool.nasa_bootcamp.databinding.LandPadItemBinding
import java.util.ArrayList

class LandAdapter internal constructor(
    context: Context,
    landModels: ArrayList<LandPadModel>,
    val onLandClicked: (landModel: LandPadModel) -> Unit
) :
    RecyclerView.Adapter<LandHolder>() {
    var context: Context
    var landModels: ArrayList<LandPadModel>

    internal interface OnLandPadClickListener {
        fun onLandPadClick(landModel: LandPadModel, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandHolder {
        return LandHolder(LandPadItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: LandHolder, position: Int) {
        val landPadModel: LandPadModel = landModels[position]

        holder.binding.nameLand.text = landPadModel.full_name
        holder.binding.locationLand.text =
            "Located in ${landPadModel.location.name}, ${landPadModel.location.region} "
        holder.binding.attemptedLandings.text =
            "Attempted landings : ${landPadModel.attempted_landings}"
        holder.binding.statusLand.text = "Status : ${landPadModel.status}"
        holder.binding.landingTypeLand.text = "Landing type : ${landPadModel.landing_type}"
        holder.binding.successfulLandings.text =
            "Successful landings : ${landPadModel.successful_landings}"
        holder.binding.descriptionLand.text = "${landPadModel.details}"

        holder.itemView.setOnClickListener {
            onLandClicked(landModels[position])
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
        this.context = context
        this.landModels = landModels
    }
}