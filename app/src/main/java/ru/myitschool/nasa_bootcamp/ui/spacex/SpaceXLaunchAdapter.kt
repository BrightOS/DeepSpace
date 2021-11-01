package ru.myitschool.nasa_bootcamp.ui.spacex

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.databinding.LaunchItemBinding
import ru.myitschool.nasa_bootcamp.utils.*
import java.util.*

class SpaceXLaunchAdapter :
    ListAdapter<SxLaunchModel, SpaceXLaunchAdapter.ViewHolder>(DiffCallback()) {

    private var lastPosition = -1

    class DiffCallback : DiffUtil.ItemCallback<SxLaunchModel>() {
        override fun areItemsTheSame(oldItem: SxLaunchModel, newItem: SxLaunchModel): Boolean {
            return oldItem.mission_name == newItem.mission_name
        }

        override fun areContentsTheSame(oldItem: SxLaunchModel, newItem: SxLaunchModel): Boolean {
            return oldItem == newItem
        }
    }

    //TODO : create such anim in all recyclers + extensions
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position)
        if (holder.absoluteAdapterPosition > lastPosition) {
            val animation = AnimationUtils.loadAnimation(holder.context, R.anim.slide_enter_anim)
            holder.itemView.startAnimation(animation)
            lastPosition = holder.absoluteAdapterPosition
        }
        holder.bind(model)
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

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(private val binding: LaunchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val context = binding.root.context!!

        private var expanded = false

        private val requestListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                binding.loadProgressbar.visibility = View.GONE
                binding.loadCard.visibility = View.GONE
                return false
            }
        }


        init {
            binding.root.setOnClickListener {
                expanded = if (expanded) {
                    binding.layoutLaunchSpacex.transitionToStart()
                    false
                } else {
                    binding.layoutLaunchSpacex.transitionToEnd()
                    true
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(launchModel: SxLaunchModel) {
            with(binding) {
                loadProgressbar.visibility = View.VISIBLE

                missionName.text = launchModel.mission_name
                missionYear.text = convertDateFromUnix(launchModel.launch_date_unix)
                details.text = launchModel.details
                launchSite.text = launchModel.launch_site.site_name_long

                val finalString = convertDateFromUnix(launchModel.launch_date_unix)

                val calendar = GregorianCalendar()
                calendar.time = Date(launchModel.launch_date_unix * 1000L)

                missionYear.text = finalString.addSubstringAtIndex(
                    getDayOfMonthSuffix(
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ),
                    finalString.indexOf('.')
                )
                characteristicsLaunch.apply {
                    rocketName.text =
                        "Rocket name: ${launchModel.rocket.rocket_name}"

                    rocketType.text =
                        "Rocket type: ${launchModel.rocket.rocket_type}"

                    flightNumber.text =
                        "Flight number: ${launchModel.rocket.first_stage.cores[0].flight}"

                    country.text =
                        "Nationality: ${launchModel.rocket.second_stage.payloads[0].nationality}"

                    block.text =
                        "Blocks: ${launchModel.rocket.second_stage.block}"

                    coreSerial.text =
                        if (launchModel.rocket.first_stage.cores[0].core_serial != null)
                            "Core serial : ${launchModel.rocket.first_stage.cores[0].core_serial}"
                        else "Core serial — "

                    reusedCore.text =
                        "Reused? : ${if (launchModel.rocket.first_stage.cores[0].reused) "Yes " else "No, not yet"}"

                    payloadId.text =
                        "Payload : ${launchModel.rocket.second_stage.payloads[0].payload_id}"

                    payloadMass.text =
                        "Payload mass : ${launchModel.rocket.second_stage.payloads[0].payload_mass_kg}"

                    payloadType.text =
                        "Payload type : ${launchModel.rocket.second_stage.payloads[0].payload_type}"

                    manufacturer.text =
                        if (manufacturer != null)
                            "Manufacturer : ${launchModel.rocket.second_stage.payloads[0].manufacturer}"
                        else "Manufacturer — "

                    refSystem.text =
                        if (launchModel.rocket.second_stage.payloads[0].reference_system != null)
                            "Payload : ${launchModel.rocket.second_stage.payloads[0].reference_system}"
                        else "Payload —  "

                    var reused = "Reused? : No"
                    if (launchModel.rocket.fairings?.reused != null)
                        reused =
                            "Reused? : ${if (launchModel.rocket.fairings.reused) "Yes " else "No, not yet"}"

                    reusedFairings.text = reused

                    var tried = "Tried to recover? : No"
                    if (launchModel.rocket.fairings?.reused != null)
                        tried =
                            "Tried to recover? : ${if (launchModel.rocket.fairings.reused) "Yes " else "No"}"

                    recoverAttempt.text = tried

                    when (launchModel.rocket.rocket_name) {
                        "Falcon 9" -> appCompatImageView.setImageResource(R.drawable.falcon9)
                        "Falcon Heavy" -> appCompatImageView.setImageResource(R.drawable.falcon_img)
                        else -> appCompatImageView.setImageResource(R.drawable.falcon9)

                    }

                    var recovered = "Recovered? : No"
                    if (launchModel.rocket.fairings?.reused != null)
                        recovered =
                            "Recovered? : ${if (launchModel.rocket.fairings.reused) "Yes " else "No"}"

                    this.recovered.text = recovered

                }

                Log.d(
                    "LAUNCH_ADAPTER_TAG",
                    "Mission patch is null? ${(launchModel.links?.mission_patch == null)}"
                )

                if (launchModel.links != null) {
                    Log.i("debug", "not null links");
                    if (launchModel.links.mission_patch != null)
                        loadImage(
                            recycleItemImg.context,
                            launchModel.links.mission_patch,
                            recycleItemImg,
                            requestListener
                        )
                    else loadImage(
                        recycleItemImg.context,
                        LAUNCH_LOGOS_GIF,
                        recycleItemImg,
                        requestListener
                    )
                } else {
                    Log.i("debug", "null links");
                    loadImage(
                        recycleItemImg.context,
                        "https://cdn.dribbble.com/users/932046/screenshots/4818792/space_dribbble.png",
                        recycleItemImg,
                        requestListener
                    )
                }

                status.text = if (launchModel.launch_success) "Status: success" else "Status: failed"

                context.getColor(if (launchModel.launch_success) R.color.green else R.color.red).let {
                    status.setTextColor(it)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        cardLaunch.outlineSpotShadowColor = it
                        cardLaunch.outlineAmbientShadowColor = it
                    }
                }

            }
        }
    }
}
