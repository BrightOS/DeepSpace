package ru.myitschool.nasa_bootcamp.ui.spacex

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.databinding.LaunchItemBinding
import java.util.ArrayList

class SpaceXLaunchAdapter(context: Context, launchModels: ArrayList<SxLaunchModel>) :
    RecyclerView.Adapter<SpaceXLaunchViewHolder>() {
    var context: Context
    var launchs: List<SxLaunchModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpaceXLaunchViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.launch_item, parent, false)

        return SpaceXLaunchViewHolder(
            LaunchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context
        )
    }


    override fun onBindViewHolder(holder: SpaceXLaunchViewHolder, position: Int) {
        val launchModel: SxLaunchModel = launchs[position]
        Log.d("Adapter_lauch_TAG", " upcoming? ${launchModel.upcoming} ")
        holder.loadImage(launchModel.links.mission_patch_small)
        holder.binding.missionName.setText(launchModel.mission_name)
        holder.binding.missionYear.setText("${launchModel.launch_year}")
    }

    override fun getItemCount(): Int {
        return launchs.size
    }

    init {
        this.context = context
        this.launchs = launchModels
    }

}