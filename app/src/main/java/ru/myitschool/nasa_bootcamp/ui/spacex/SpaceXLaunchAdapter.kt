package ru.myitschool.nasa_bootcamp.ui.spacex

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import java.util.ArrayList

class SpaceXLaunchAdapter(context: Context, roverModels: ArrayList<SxLaunchModel>) : RecyclerView.Adapter<SpaceXLaunchViewHolder>() {
    var context: Context
    var launchs: List<SxLaunchModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpaceXLaunchViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.launch_item, parent, false)


        return SpaceXLaunchViewHolder(context, view)
    }

    override fun onBindViewHolder(holder: SpaceXLaunchViewHolder, position: Int) {
        val launchModel: SxLaunchModel = launchs[position]
         holder.loadImage(launchModel.links.mission_patch_small)
    }

    override fun getItemCount(): Int {
        return launchs.size
    }

    fun update(modelList: ArrayList<SxLaunchModel>){
        launchs = modelList
        notifyDataSetChanged()
    }

    init {
        this.context = context
        this.launchs = roverModels
    }

}