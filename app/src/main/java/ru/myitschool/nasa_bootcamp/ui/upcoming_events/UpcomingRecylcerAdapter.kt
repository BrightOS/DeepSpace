package ru.myitschool.nasa_bootcamp.ui.upcoming_events

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.R
import ru.myitschool.nasa_bootcamp.data.model.NotificationModel
import ru.myitschool.nasa_bootcamp.data.model.UpcomingLaunchModel
import ru.myitschool.nasa_bootcamp.databinding.UpcomingItemBinding
import ru.myitschool.nasa_bootcamp.utils.NotificationCentre
import ru.myitschool.nasa_bootcamp.utils.convertDateFromUnix
import ru.myitschool.nasa_bootcamp.utils.getColorFromAttributes
import ru.myitschool.nasa_bootcamp.utils.loadImage


class UpcomingRecylcerAdapter internal constructor(
    var context: Context,
    var upcomingLaunches: List<UpcomingLaunchModel>
) :
    RecyclerView.Adapter<UpcomingRecyclerHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingRecyclerHolder {

        return UpcomingRecyclerHolder(
            UpcomingItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context
        )
    }


    override fun onBindViewHolder(holder: UpcomingRecyclerHolder, position: Int) {
        val upcomingLaunchModel: UpcomingLaunchModel = upcomingLaunches[position]
        var isEnabledNotification = false
        var associatedNotification: NotificationModel? = null

        holder.binding.apply {
            missionName.text = upcomingLaunchModel.name
            if (upcomingLaunchModel.static_fire_date_unix != null)
                missionDate.text =
                    convertDateFromUnix(upcomingLaunchModel.static_fire_date_unix)

            Log.d("UPCOMING_ADAPTER_TAG", "Patch is null : ${upcomingLaunchModel.patch == null}")
            if (upcomingLaunchModel.patch != null) {
                loadImage(
                    context,
                    upcomingLaunchModel.patch.small,
                    recycleItemImg,
                    holder.requestListener
                )
                println(upcomingLaunchModel.patch.small)
            }

            for (notification in NotificationCentre().getAllScheduledNotifications(context)) {
                if (upcomingLaunchModel == notification.launchModel) {
                    buttonText.text = "Disable notification"
                    isEnabledNotification = true
                    associatedNotification = notification

                    context.resources.getColor(R.color.green).let {
                        enableNotificationButton.setCardBackgroundColor(
                            ColorStateList.valueOf(it)
                        )

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            cardLaunch.outlineSpotShadowColor = it
                            cardLaunch.outlineAmbientShadowColor = it
                        }
                    }
                    break
                }
            }
            if (!upcomingLaunchModel.upcoming!!) {
                enableNotificationButton.isEnabled = false
            }
            enableNotificationButton.setOnClickListener {
                if (isEnabledNotification) {
                    NotificationCentre().cancelNotification(context, associatedNotification!!)
                    buttonText.text = "Enable notification"
                    isEnabledNotification = false
                    associatedNotification = null
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        animateShadow(
                            cardLaunch.outlineSpotShadowColor,
                            context.resources.getColor(
                                R.color.upcoming_blue
                            ),
                            holder.binding
                        )
                    }
                    animateButton(
                        enableNotificationButton.cardBackgroundColor.defaultColor,
                        getColorFromAttributes(context, R.attr.buttonBackgroundColor),
                        holder.binding
                    )
                } else if (upcomingLaunchModel.static_fire_date_unix != null) {
                    associatedNotification = NotificationCentre().scheduleNotification(
                        context,
                        "Launch begins",
                        "${missionName.text.toString()} launch starts now!",
                        upcomingLaunchModel.static_fire_date_unix,
                        upcomingLaunchModel
                    )
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        animateShadow(
                            cardLaunch.outlineSpotShadowColor,
                            context.resources.getColor(
                                R.color.green
                            ),
                            holder.binding
                        )
                    }
                    animateButton(
                        enableNotificationButton.cardBackgroundColor.defaultColor,
                        context.resources.getColor(
                            R.color.green
                        ),
                        holder.binding
                    )
                    buttonText.text = "Disable notification"
                    isEnabledNotification = true
                }
            }
            loadProgressbar.visibility = View.GONE
            recycleItemImg.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return upcomingLaunches.size
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun animateShadow(colorFrom: Int, colorTo: Int, binding: UpcomingItemBinding) {
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = 500 // milliseconds

        colorAnimation.addUpdateListener { animator ->
            binding.cardLaunch.outlineSpotShadowColor = animator.animatedValue as Int
            binding.cardLaunch.outlineAmbientShadowColor = animator.animatedValue as Int
        }

        colorAnimation.start()
    }

    private fun animateButton(colorFrom: Int, colorTo: Int, binding: UpcomingItemBinding) {
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = 500 // milliseconds

        colorAnimation.addUpdateListener { animator ->
            binding.enableNotificationButton.setCardBackgroundColor(
                ColorStateList.valueOf(
                    animator.animatedValue as Int
                )
            )
        }

        colorAnimation.start()
    }

}