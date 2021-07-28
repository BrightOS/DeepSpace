package ru.myitschool.nasa_bootcamp.ui.spacex

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rocket_item.view.*
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.databinding.LaunchItemBinding
import ru.myitschool.nasa_bootcamp.ui.animation.animateIt
import ru.myitschool.nasa_bootcamp.utils.loadImage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.exp

class SpaceXLaunchAdapter internal constructor(
    context: Context,
    launchModels: ArrayList<SxLaunchModel>
) :
    RecyclerView.Adapter<SpaceXLaunchViewHolder>() {
    var context: Context
    var launchs: List<SxLaunchModel>

    internal interface OnLaunchClickListener {
        fun onLaunchClick(launch: SxLaunchModel?, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpaceXLaunchViewHolder {

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


        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val date = Date(launchModel.launch_date_unix * 1000L)

        holder.binding.missionName.setText(launchModel.mission_name)
        holder.binding.missionYear.setText("${sdf.format(date)}")
        holder.binding.details.setText(launchModel.details)

        holder.binding.characteristicsLaunch.rocketName.text = launchModel.rocket.rocket_name


        if (launchModel.links.mission_patch != null)
            loadImage(context, launchModel.links.mission_patch, holder.binding.recycleItemImg)
        else loadImage(
            context,
            "https://cdn.dribbble.com/users/932046/screenshots/4818792/space_dribbble.png",
            holder.binding.recycleItemImg
        )


        val onLaunchClickListener = object : SpaceXLaunchAdapter.OnLaunchClickListener {
            override fun onLaunchClick(launch: SxLaunchModel?, position: Int) {

                animateIt {
                    animate(holder.binding.status) animateTo {
                        if (!holder.expanded) {
                            paddingBottom(holder.binding.details.length().toFloat()/4)
                            holder.binding.details.visibility = View.VISIBLE
                            holder.binding.characteristicsLaunch.root.visibility = View.VISIBLE
                            holder.expanded = true
                        } else {
                            paddingBottom(0f)
                            holder.binding.details.visibility = View.GONE
                            holder.binding.characteristicsLaunch.root.visibility = View.GONE
                            holder.expanded = false
                        }
                    }
                }.start()
            }
        }

        holder.itemView.setOnClickListener(View.OnClickListener {
            onLaunchClickListener.onLaunchClick(
                launchs.get(position),
                position
            )
        })

        if (launchModel.launch_success) holder.binding.status.setText("Status: success") else holder.binding.status.setText(
            "Status: failed"
        )
    }

    override fun getItemCount(): Int {
        return launchs.size
    }

    init {
        this.context = context
        this.launchs = launchModels
    }

}