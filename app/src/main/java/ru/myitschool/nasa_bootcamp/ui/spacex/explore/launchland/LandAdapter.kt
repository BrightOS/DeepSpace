package ru.myitschool.nasa_bootcamp.ui.spacex.explore.launchland

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.data.model.LandPadModel
import ru.myitschool.nasa_bootcamp.databinding.FragmentMapsBinding
import ru.myitschool.nasa_bootcamp.databinding.LandPadItemBinding
import java.util.ArrayList

class LandAdapter  internal constructor(
    context: Context,
    landModels: ArrayList<LandPadModel>,
    // val navController: NavController
) :
    RecyclerView.Adapter<LandHolder>() {
    var context: Context
    var landModels: ArrayList<LandPadModel>



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandHolder {
        return LandHolder(
            LandPadItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context
        )
    }

    override fun onBindViewHolder(holder: LandHolder, position: Int) {
        val landPadModel : LandPadModel = landModels[position]


        holder.binding.nameLand.text = landPadModel.full_name
        holder.binding.attemptedLandings.text = "Attempted landings : ${landPadModel.location.attempted_landings}"
        holder.binding.statusLand.text = "Status : ${landPadModel.status}"
        holder.binding.landingTypeLand.text = "Landing type : ${landPadModel.location.landing_type}"
        holder.binding.successfulLandings.text = "Successful landings : ${landPadModel.location.successful_landings}"
        holder.binding.descriptionLand.text = "${landPadModel.location.details}"

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