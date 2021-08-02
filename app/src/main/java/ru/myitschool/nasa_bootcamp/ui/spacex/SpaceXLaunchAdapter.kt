package ru.myitschool.nasa_bootcamp.ui.spacex

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.xml.sax.ErrorHandler
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.databinding.LaunchItemBinding
import ru.myitschool.nasa_bootcamp.utils.Status
import ru.myitschool.nasa_bootcamp.utils.convertDateFromUnix
import ru.myitschool.nasa_bootcamp.utils.loadImage

class SpaceXLaunchAdapter : ListAdapter<SxLaunchModel, SpaceXLaunchAdapter.ViewHolder>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<SxLaunchModel>() {
        override fun areItemsTheSame(oldItem: SxLaunchModel, newItem: SxLaunchModel): Boolean {
            return oldItem.mission_name == newItem.mission_name
        }

        override fun areContentsTheSame(oldItem: SxLaunchModel, newItem: SxLaunchModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position)
        holder.bind(model, position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LaunchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    class ViewHolder(private val binding: LaunchItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val context = binding.root.context

        var expanded = false

        init {
            binding.root.setOnClickListener {
                expanded = if (expanded){
                    binding.layoutLaunchSpacex.transitionToStart()
                    false
                }else{
                    binding.layoutLaunchSpacex.transitionToEnd()
                    true
                }
            }
        }

        fun bind(launchModel : SxLaunchModel, position: Int) {
            lateinit var viewModel: LaunchItemViewModel

            var expanded = false


            var requestListener = object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                    binding.loadProgressbar.visibility = View.GONE
                    binding.layoutLaunchSpacex.visibility = View.VISIBLE
                    return false
                }
            }

            binding.missionName.text = launchModel.mission_name
            binding.missionYear.text = "${convertDateFromUnix(launchModel.launch_date_unix)}"
            binding.details.text = launchModel.details
            binding.launchSite.text = launchModel.launch_site.site_name_long


            binding.characteristicsLaunch.rocketName.text = "Rocket name: ${launchModel.rocket.rocket_name}"

            binding.characteristicsLaunch.rocketType.text = "Rocket type: ${launchModel.rocket.rocket_type}"

            binding.characteristicsLaunch.flightNumber.text = "Flight number: ${launchModel.rocket.first_stage.cores[0].flight}"

            binding.characteristicsLaunch.country.text = "Nationality: ${launchModel.rocket.second_stage.payloads[0].nationality}"

            binding.characteristicsLaunch.block.text = "Blocks: ${launchModel.rocket.second_stage.block}"

            binding.characteristicsLaunch.coreSerial.text = "Core serial : ${launchModel.rocket.first_stage.cores[0].core_serial}"

            binding.characteristicsLaunch.reusedCore.text = "Reused? : ${if (launchModel.rocket.first_stage.cores[0].reused) "Yes " else "No, not yet"}"

            binding.characteristicsLaunch.payloadId.text = "Payload : ${launchModel.rocket.second_stage.payloads[0].payload_id}"

            binding.characteristicsLaunch.payloadMass.text = "Payload mass : ${launchModel.rocket.second_stage.payloads[0].payload_mass_kg}"

            binding.characteristicsLaunch.payloadType.text = "Payload type : ${launchModel.rocket.second_stage.payloads[0].payload_type}"

            binding.characteristicsLaunch.manufacturer.text = "Manufacturer : ${launchModel.rocket.second_stage.payloads[0].manufacturer}"

            binding.characteristicsLaunch.refSystem.text = "Payload : ${launchModel.rocket.second_stage.payloads[0].reference_system}"

            var reused = "Reused? : No"
            if (launchModel.rocket.fairings != null && launchModel.rocket.fairings.reused != null)
                reused =
                    "Reused? : ${if (launchModel.rocket.fairings.reused!!) "Yes " else "No, not yet"}"

            binding.characteristicsLaunch.reusedFairings.text = reused

            var tried = "Tried to recover? : No"
            if (launchModel.rocket.fairings != null && launchModel.rocket.fairings.reused != null)
                tried =
                    "Tried to recover? : ${if (launchModel.rocket.fairings.reused!!) "Yes " else "No"}"

            binding.characteristicsLaunch.recoverAttempt.text = tried

            var recovered = "Recovered? : No"
            if (launchModel.rocket.fairings != null && launchModel.rocket.fairings.reused != null)
                recovered =
                    "Recovered? : ${if (launchModel.rocket.fairings.reused!!) "Yes " else "No"}"

            binding.characteristicsLaunch.recovered.text = recovered


            Log.d("LAUNCH_ADAPTER_TAG", "Mission patch is null? ${(launchModel.links?.mission_patch == null)}")

            if(launchModel.links != null){
                if (launchModel.links.mission_patch != null)
                    loadImage(
                        binding.recycleItemImg.context,
                        launchModel.links.mission_patch,
                        binding.recycleItemImg,
                        requestListener
                    )
                else loadImage(
                    binding.recycleItemImg.context,
                    "https://cdn.dribbble.com/users/932046/screenshots/4818792/space_dribbble.png",
                    binding.recycleItemImg,
                    requestListener
                )
            }
            if (launchModel.launch_success) {
                binding.status.setText("Status: success")
                binding.status.setTextColor(context.getColor(R.color.green))
            } else {
                binding.status.setText("Status: failed")
                binding.status.setTextColor(context.getColor(R.color.red))
            }
        }
    }

}