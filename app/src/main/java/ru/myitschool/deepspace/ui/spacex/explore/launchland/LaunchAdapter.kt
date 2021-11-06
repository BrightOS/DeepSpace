package ru.myitschool.deepspace.ui.spacex.explore.launchland

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.deepspace.data.model.LaunchPadModel
import ru.myitschool.deepspace.databinding.LaunchPadItemBinding
import ru.myitschool.deepspace.utils.capitalize
import java.util.*

class LaunchAdapter internal constructor(
    launchModel: ArrayList<LaunchPadModel>,
    val navController: NavController
) :
    RecyclerView.Adapter<LaunchHolder>() {
    private var launchModels: ArrayList<LaunchPadModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchHolder {
        return LaunchHolder(
            LaunchPadItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    internal interface OnLaunchPadClickListener {
        fun onLaunchPadClick(launchModel: LaunchPadModel, position: Int)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LaunchHolder, position: Int) {
        val launchModel: LaunchPadModel = launchModels[position]

        holder.binding.nameLaunchPad.text = launchModel.name
        holder.binding.attempedSuccessfulLaunches.text =
            "${launchModel.attempted_launches} / ${launchModel.successful_launches}"

        holder.binding.vehiclesLaunched.text = "${launchModel.vehicles_launched}"
        holder.binding.locationLaunchPad.text = launchModel.site_name_long
        holder.binding.descriptionLand.text = launchModel.details
        holder.binding.statusLaunchPad.text = capitalize(launchModel.status)

        val onLaunchPadClickListener = object : OnLaunchPadClickListener {
            override fun onLaunchPadClick(launchModel: LaunchPadModel, position: Int) {
                val action = LaunchLandFragmentDirections.actionToLandMap(
                    launchModel.location.longitude.toFloat(),
                    launchModel.location.latitude.toFloat(),
                    launchModel.name,
                    launchModel.details
                )

                navController.navigate(action)
            }
        }

        holder.itemView.setOnClickListener {
            onLaunchPadClickListener.onLaunchPadClick(
                launchModels.get(position),
                position
            )
        }
    }

    override fun getItemCount(): Int {
        return launchModels.size
    }

    fun update(modelList: ArrayList<LaunchPadModel>) {
        launchModels = modelList
        notifyDataSetChanged()
    }

    init {
        this.launchModels = launchModel
    }
}
