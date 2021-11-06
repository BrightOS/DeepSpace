package ru.myitschool.deepspace.ui.upcoming_events

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import ru.myitschool.deepspace.R
import ru.myitschool.deepspace.data.model.NotificationModel
import ru.myitschool.deepspace.data.model.UpcomingLaunchModel
import ru.myitschool.deepspace.databinding.UpcomingItemBinding
import ru.myitschool.deepspace.utils.*
import java.util.*


class UpcomingRecylcerAdapter internal constructor(
    var context: Context,
    private var upcomingLaunches: ArrayList<UpcomingLaunchModel>,
) :
    RecyclerView.Adapter<UpcomingRecyclerHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UpcomingRecyclerHolder(
            UpcomingItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UpcomingRecyclerHolder, position: Int) {
        val upcomingLaunchModel: UpcomingLaunchModel = upcomingLaunches[position]
        var isEnabledNotification = false
        var associatedNotification: NotificationModel? = null
        holder.setIsRecyclable(false)

        holder.binding.apply {
            missionName.text = upcomingLaunchModel.name
            val finalString: String
            if (upcomingLaunchModel.static_fire_date_unix != null) {
                finalString = convertDateFromUnix(upcomingLaunchModel.static_fire_date_unix)

                val calendar = GregorianCalendar()
                calendar.time = Date(upcomingLaunchModel.static_fire_date_unix * 1000)

                missionDate.text = finalString.addSubstringAtIndex(
                    getDayOfMonthSuffix(
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ),
                    finalString.indexOf('.')
                )
            } else {
                finalString = ""
            }

            Log.d("UPCOMING_ADAPTER_TAG", "Patch is null : ${upcomingLaunchModel.patch == null}")
            if (upcomingLaunchModel.patch != null) {
                loadImage(
                    context,
                    upcomingLaunchModel.patch.small,
                    recycleItemImg,
                    holder.requestListener
                )
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
                            holder.binding.cardLaunch
                        )
                    }
                    animateButton(
                        enableNotificationButton.cardBackgroundColor.defaultColor,
                        getColorFromAttributes(context, R.attr.buttonBackgroundColor),
                        holder.binding
                    )
                } else if (holder.binding.missionDate.text != null) {
                    associatedNotification = NotificationCentre().scheduleNotification(
                        context,
                        "Launch begins",
                        "${holder.binding.missionName.text} launch starts now!",
                        "${finalString}:00",
                        upcomingLaunchModel
                    )
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        animateShadow(
                            cardLaunch.outlineSpotShadowColor,
                            context.resources.getColor(
                                R.color.green
                            ),
                            holder.binding.cardLaunch
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
    private fun animateShadow(colorFrom: Int, colorTo: Int, view: MaterialCardView) {
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = 500 // milliseconds

        colorAnimation.addUpdateListener { animator ->
            view.outlineSpotShadowColor = animator.animatedValue as Int
            view.outlineAmbientShadowColor = animator.animatedValue as Int
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
