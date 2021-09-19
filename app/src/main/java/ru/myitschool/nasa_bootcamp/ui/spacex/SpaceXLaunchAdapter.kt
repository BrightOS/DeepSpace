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
import androidx.transition.TransitionManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.transition.MaterialSharedAxis
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.SxLaunchModel
import ru.myitschool.nasa_bootcamp.databinding.LaunchItemBinding
import ru.myitschool.nasa_bootcamp.utils.*
import java.util.*

class SpaceXLaunchAdapter :
    ListAdapter<SxLaunchModel, SpaceXLaunchAdapter.ViewHolder>(DiffCallback()) {

    var lastPosition = -1

    class DiffCallback : DiffUtil.ItemCallback<SxLaunchModel>() {
        override fun areItemsTheSame(oldItem: SxLaunchModel, newItem: SxLaunchModel): Boolean {
            return oldItem.mission_name == newItem.mission_name
        }

        override fun areContentsTheSame(oldItem: SxLaunchModel, newItem: SxLaunchModel): Boolean {
            return oldItem == newItem
        }
    }

    //TODO : сделать такую анимацию во всех ресайклах + эктеншн
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = getItem(position)
        if(holder.absoluteAdapterPosition > lastPosition){
            val animation = AnimationUtils.loadAnimation(holder.context,R.anim.slide_enter_anim)
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
        val context = binding.root.context

        var expanded = false

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
            binding.loadProgressbar.visibility = View.VISIBLE

            binding.missionName.text = launchModel.mission_name
            binding.missionYear.text = convertDateFromUnix(launchModel.launch_date_unix)
            binding.details.text = launchModel.details
            binding.launchSite.text = launchModel.launch_site.site_name_long

            val finalString = convertDateFromUnix(launchModel.launch_date_unix)

            val calendar = GregorianCalendar()
            calendar.time = Date(launchModel.launch_date_unix * 1000L)

            binding.missionYear.text = finalString.addSubstringAtIndex(
                getDayOfMonthSuffix(
                    calendar.get(Calendar.DAY_OF_MONTH)
                ),
                finalString.indexOf('.')
            )
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
                if (launchModel.rocket.first_stage.cores[0].core_serial != null)
                    "Core serial : ${launchModel.rocket.first_stage.cores[0].core_serial}"
                else "Core serial — "

            binding.characteristicsLaunch.reusedCore.text =
                "Reused? : ${if (launchModel.rocket.first_stage.cores[0].reused) "Yes " else "No, not yet"}"

            binding.characteristicsLaunch.payloadId.text =
                "Payload : ${launchModel.rocket.second_stage.payloads[0].payload_id}"

            binding.characteristicsLaunch.payloadMass.text =
                "Payload mass : ${launchModel.rocket.second_stage.payloads[0].payload_mass_kg}"

            binding.characteristicsLaunch.payloadType.text =
                "Payload type : ${launchModel.rocket.second_stage.payloads[0].payload_type}"

            binding.characteristicsLaunch.manufacturer.text =
                if( binding.characteristicsLaunch.manufacturer!=null)
                "Manufacturer : ${launchModel.rocket.second_stage.payloads[0].manufacturer}"
            else "Manufacturer — "

            binding.characteristicsLaunch.refSystem.text =
                if (launchModel.rocket.second_stage.payloads[0].reference_system != null)
                    "Payload : ${launchModel.rocket.second_stage.payloads[0].reference_system}"
                else "Payload —  "

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

            when(launchModel.rocket.rocket_name){
                "Falcon 9"-> binding.characteristicsLaunch.appCompatImageView.setImageResource(R.drawable.falcon9)
                "Falcon Heavy"-> binding.characteristicsLaunch.appCompatImageView.setImageResource(R.drawable.falcon_img)
                else-> binding.characteristicsLaunch.appCompatImageView.setImageResource(R.drawable.falcon9)

            }

            var recovered = "Recovered? : No"
            if (launchModel.rocket.fairings?.reused != null)
                recovered =
                    "Recovered? : ${if (launchModel.rocket.fairings.reused!!) "Yes " else "No"}"

            binding.characteristicsLaunch.recovered.text = recovered


            Log.d(
                "LAUNCH_ADAPTER_TAG",
                "Mission patch is null? ${(launchModel.links?.mission_patch == null)}"
            )

            if (launchModel.links != null) {
                Log.i("debug","not null links");
                if (launchModel.links.mission_patch != null)
                    loadImage(
                        binding.recycleItemImg.context,
                        launchModel.links.mission_patch,
                        binding.recycleItemImg,
                        requestListener
                    )
                else loadImage(
                    binding.recycleItemImg.context,
                    LAUNCH_LOGOS_GIF,
                    binding.recycleItemImg,
                    requestListener
                )
            } else {
                Log.i("debug","null links");
                loadImage(
                    binding.recycleItemImg.context,
                    "https://cdn.dribbble.com/users/932046/screenshots/4818792/space_dribbble.png",
                    binding.recycleItemImg,
                    requestListener
                )
            }
            if (launchModel.launch_success) {
                binding.status.setText("Status: success")

                context.getColor(R.color.green).let {
                    binding.status.setTextColor(it)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        binding.cardLaunch.outlineSpotShadowColor = it
                        binding.cardLaunch.outlineAmbientShadowColor = it
                    }
                }
            } else {
                binding.status.setText("Status: failed")

                context.getColor(R.color.red).let {
                    binding.status.setTextColor(it)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        binding.cardLaunch.outlineSpotShadowColor = it
                        binding.cardLaunch.outlineAmbientShadowColor = it
                    }
                }
            }
        }
    }

}