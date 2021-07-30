package ru.myitschool.nasa_bootcamp.ui.spacex

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.databinding.LaunchItemBinding
import ru.myitschool.nasa_bootcamp.ui.animation.animateIt
import ru.myitschool.nasa_bootcamp.utils.convertDateFromUnix
import ru.myitschool.nasa_bootcamp.utils.loadImage

class SpaceXLaunchAdapter() :
    ListAdapter<SxLaunchModel, SpaceXLaunchAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: LaunchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var expanded = false
        fun bind(
            launchModel: SxLaunchModel,
            position: Int
        ) {
            binding.missionName.text = launchModel.mission_name
            binding.missionYear.text = "${convertDateFromUnix(launchModel.launch_date_unix)}"
            binding.details.text = launchModel.details
            binding.launchSite.text = launchModel.launch_site.site_name_long


            binding.characteristicsLaunch.rocketName.text =
                "Rocket name: ${launchModel.rocket.rocket_name}"

            binding.characteristicsLaunch.rocketType.text =
                "Rocket type: ${launchModel.rocket.rocket_type}"

            binding.characteristicsLaunch.flightNumber.text =
                "Flight number: ${launchModel.rocket.first_stage.cores[0].flight}"

            binding.characteristicsLaunch.country.text =
                "Nationality: ${launchModel.rocket.second_stage.payloads[0].nationality}"

            binding.characteristicsLaunch.block.text =
                "Blocks: ${launchModel.rocket.second_stage.block}"

            binding.characteristicsLaunch.coreSerial.text =
                "Core serial : ${launchModel.rocket.first_stage.cores[0].core_serial}"

            binding.characteristicsLaunch.reusedCore.text =
                "Reused? : ${if (launchModel.rocket.first_stage.cores[0].reused) "Yes " else "No, not yet"}"

            binding.characteristicsLaunch.payloadId.text =
                "Payload : ${launchModel.rocket.second_stage.payloads[0].payload_id}"

            binding.characteristicsLaunch.payloadMass.text =
                "Payload mass : ${launchModel.rocket.second_stage.payloads[0].payload_mass_kg}"

            binding.characteristicsLaunch.payloadType.text =
                "Payload type : ${launchModel.rocket.second_stage.payloads[0].payload_type}"

            binding.characteristicsLaunch.manufacturer.text =
                "Manufacturer : ${launchModel.rocket.second_stage.payloads[0].manufacturer}"

            binding.characteristicsLaunch.refSystem.text =
                "Payload : ${launchModel.rocket.second_stage.payloads[0].reference_system}"

            Log.d("CHECK", "Is null? ${launchModel.rocket.fairings == null}")
            var reused = "Reused? : No"
            if (launchModel.rocket.fairings != null && launchModel.rocket.fairings.reused != null)
                reused =
                    "Reused? : ${if (launchModel.rocket.fairings.reused) "Yes " else "No, not yet"}"

            binding.characteristicsLaunch.reusedFairings.text = reused

            var tried = "Tried to recover? : No"
            if (launchModel.rocket.fairings != null && launchModel.rocket.fairings.reused != null)
                tried =
                    "Tried to recover? : ${if (launchModel.rocket.fairings.reused) "Yes " else "No"}"

            binding.characteristicsLaunch.recoverAttempt.text = tried

            var recovered = "Recovered? : No"
            if (launchModel.rocket.fairings != null && launchModel.rocket.fairings.reused != null)
                recovered =
                    "Recovered? : ${if (launchModel.rocket.fairings.reused) "Yes " else "No"}"

            binding.characteristicsLaunch.recovered.text = recovered


            if (launchModel.links.mission_patch != null)
                loadImage(
                    binding.recycleItemImg.context,
                    launchModel.links.mission_patch,
                    binding.recycleItemImg
                )
            else loadImage(
                binding.recycleItemImg.context,
                "https://cdn.dribbble.com/users/932046/screenshots/4818792/space_dribbble.png",
                binding.recycleItemImg
            )

            val onLaunchClickListener = object : OnLaunchClickListener {
                override fun onLaunchClick(launch: SxLaunchModel?, position: Int) {

                    animateIt {
                        animate(binding.status) animateTo {
                            if (!expanded) {
                                paddingBottom(binding.details.length().toFloat() / 4)
                                binding.details.visibility = View.VISIBLE
                                binding.characteristicsLaunch.root.visibility = View.VISIBLE
                                expanded = true
                            } else {
                                paddingBottom(0f)
                                binding.details.visibility = View.GONE
                                binding.characteristicsLaunch.root.visibility = View.GONE
                                expanded = false
                            }
                        }
                    }.start()
                }
            }

            itemView.setOnClickListener {
                onLaunchClickListener.onLaunchClick(
                    launchModel,
                    position
                )
            }

            if (launchModel.launch_success) binding.status.setText("Status: success") else binding.status.setText(
                "Status: failed"
            )
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<SxLaunchModel>() {
        override fun areItemsTheSame(oldItem: SxLaunchModel, newItem: SxLaunchModel): Boolean {
            return oldItem.mission_name == newItem.mission_name
        }

        override fun areContentsTheSame(oldItem: SxLaunchModel, newItem: SxLaunchModel): Boolean {
            return oldItem == newItem
        }
    }

    interface OnLaunchClickListener {
        fun onLaunchClick(launch: SxLaunchModel?, position: Int)
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

}