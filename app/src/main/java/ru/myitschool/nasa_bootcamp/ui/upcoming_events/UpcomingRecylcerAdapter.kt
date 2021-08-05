package ru.myitschool.nasa_bootcamp.ui.upcoming_events

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.data.model.NotificationModel
import ru.myitschool.nasa_bootcamp.data.model.UpcomingLaunchModel
import ru.myitschool.nasa_bootcamp.databinding.UpcomingItemBinding
import ru.myitschool.nasa_bootcamp.utils.NotificationCentre
import ru.myitschool.nasa_bootcamp.utils.convertDateFromUnix
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

        holder.binding.missionName.text = upcomingLaunchModel.name
        if (upcomingLaunchModel.static_fire_date_unix != null)
            holder.binding.missionDate.text =
                convertDateFromUnix(upcomingLaunchModel.static_fire_date_unix).toString()

        Log.d("UPCOMING_ADAPTER_TAG", "Patch is null : ${upcomingLaunchModel.patch == null}")
        if (upcomingLaunchModel.patch != null)
            loadImage(context, upcomingLaunchModel.patch.small, holder.binding.recycleItemImg, holder.requestListener)

        for(notification in NotificationCentre().getAllScheduledNotifications(context)) {
            if (upcomingLaunchModel == notification.launchModel) {
                holder.binding.enableNotificationButton.text = "Disable notification"
                isEnabledNotification = true
                associatedNotification = notification
                break
            }
        }
        if (!upcomingLaunchModel.upcoming!!) {
            holder.binding.enableNotificationButton.isEnabled = false
        }
        holder.binding.enableNotificationButton.setOnClickListener {
            if (isEnabledNotification) {
                NotificationCentre().cancelNotification(context, associatedNotification!!)
                holder.binding.enableNotificationButton.text = "Enable notification"
                isEnabledNotification = false
                associatedNotification = null
            }
            else if (holder.binding.missionDate.text != null) {
                associatedNotification = NotificationCentre().scheduleNotification(context, "Launch begins", "${holder.binding.missionName.text.toString()} launch starts now!", "${holder.binding.missionDate.text.toString()}:00", upcomingLaunchModel)
                holder.binding.enableNotificationButton.text = "Disable notification"
                isEnabledNotification = true
            }
        }
        holder.binding.loadProgressbar.visibility = View.GONE
        holder.binding.recycleItemImg.visibility = View.VISIBLE

    }

    override fun getItemCount(): Int {
        return upcomingLaunches.size
    }

}