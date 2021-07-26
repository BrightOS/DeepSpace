package ru.myitschool.nasa_bootcamp.ui.spacex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.databinding.LaunchItemBinding
import ru.myitschool.nasa_bootcamp.ui.animation.animateIt
import ru.myitschool.nasa_bootcamp.utils.loadImage
import java.text.SimpleDateFormat
import java.util.*

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

        animateIt {
            animate(holder.binding.infoLayoutItem) animateTo {
                    invisible()
            }
        }.start()

        holder.binding.missionName2.visibility = View.VISIBLE
        holder.binding.missionYear2.visibility = View.VISIBLE
        holder.binding.recycleItemImg2.visibility = View.VISIBLE
        loadImage(context, launchModel.links.mission_patch_small, holder.binding.recycleItemImg)


        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val date = Date(launchModel.launch_date_unix * 1000L)

        holder.binding.missionName.setText(launchModel.mission_name)
        holder.binding.missionYear.setText("${sdf.format(date)}")

        holder.binding.missionName2.setText(launchModel.mission_name)
        holder.binding.missionYear2.setText("${sdf.format(date)}")

        loadImage(context, launchModel.links.mission_patch_small, holder.binding.recycleItemImg2)

        val onLaunchClickListener = object : SpaceXLaunchAdapter.OnLaunchClickListener {
            override fun onLaunchClick(launch: SxLaunchModel?, position: Int) {
///
                animateIt {
                    animate(holder.binding.recycleItemImg) animateTo {
                        if(holder.binding.recycleItemImg.paddingBottom < 50)
                        paddingBottom(55f)
                        else {
                            paddingBottom(0f)
                            marginLeft(55f)
                        }
                    }
                    animate(holder.binding.missionName) animateTo {
                        paddingBottom(32f)
                    }

                    animate( holder.binding.missionYear) animateTo {
                        paddingLeft(24f)
                        paddingRight(24f)
                    }
                }.start()

                ///

                animateIt(duration = 1000L) {
                    animate(holder.binding.defaultLayoutItem) animateTo {
                        if (holder.binding.defaultLayoutItem.visibility == View.VISIBLE)
                            invisible()
                        else visible()
                    }
                }.start()

                animateIt {
                    animate(holder.binding.infoLayoutItem) animateTo {
                        if (holder.binding.infoLayoutItem.visibility == View.VISIBLE)
                            invisible()
                        else visible()
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