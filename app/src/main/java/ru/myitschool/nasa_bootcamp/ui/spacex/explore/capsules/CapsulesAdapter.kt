package ru.myitschool.nasa_bootcamp.ui.spacex.explore.capsules

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.data.model.CapsuleModel
import ru.myitschool.nasa_bootcamp.data.model.CoreModel
import ru.myitschool.nasa_bootcamp.databinding.CapsuleItemBinding
import ru.myitschool.nasa_bootcamp.databinding.CoreItemBinding
import ru.myitschool.nasa_bootcamp.ui.spacex.explore.cores.CoresViewHolder
import ru.myitschool.nasa_bootcamp.utils.addSubstringAtIndex
import ru.myitschool.nasa_bootcamp.utils.capitalize
import ru.myitschool.nasa_bootcamp.utils.convertDateFromUnix
import ru.myitschool.nasa_bootcamp.utils.getDayOfMonthSuffix
import java.util.*

class CapsulesAdapter internal constructor(
    context: Context,
    capsuleModels: ArrayList<CapsuleModel>,
    // val navController: NavController
) :
    RecyclerView.Adapter<CapsulesViewHolder>() {
    var context: Context
    var capsuleodels: ArrayList<CapsuleModel>

    internal interface onCapsuleClickListener {
        fun onCapsuleClick(capsuleModel: CapsuleModel, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapsulesViewHolder {
        return CapsulesViewHolder(
            CapsuleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context
        )
    }

    override fun onBindViewHolder(holder: CapsulesViewHolder, position: Int) {
        val capsuleModel: CapsuleModel = capsuleodels[position]

        if (capsuleModel.details != null)
            holder.binding.capsuleItemDescription.text = "${capsuleModel.details}"
        else {
            holder.binding.capsuleItemDescription.visibility = View.GONE
            holder.binding.separator.visibility = View.GONE
        }

        holder.binding.capsuleItemSerialTitle.text = "Capsule:"
        holder.binding.capsuleTypeItemTitle.text = "Type:"
        holder.binding.capsuleItemStatusTitle.text = "Status:"
        holder.binding.capsuleItemLandingsTitle.text = "Number of landings:"
        holder.binding.capsuleItemMissionsTitle.text = "Mission:"

        holder.binding.capsuleItemSerial.text = capsuleModel.capsule_serial
        holder.binding.capsuleItemLandings.text = "${capsuleModel.landings}"
        if (capsuleModel.missions.size > 0)
            holder.binding.capsuleItemMissions.text = capsuleModel.missions[0].name
        holder.binding.capsuleItemStatus.text = capitalize(capsuleModel.status)
        holder.binding.capsuleTypeItem.text = capsuleModel.type

        val finalString = convertDateFromUnix(capsuleModel.original_launch_unix)

        val calendar = GregorianCalendar()
        calendar.time = Date(capsuleModel.original_launch_unix * 1000L)

        holder.binding.capsuleLaunchDate.text = finalString.addSubstringAtIndex(
            getDayOfMonthSuffix(
                calendar.get(Calendar.DAY_OF_MONTH)
            ),
            finalString.indexOf('.')
        )

        val onCapsuleClickListener = object : onCapsuleClickListener {
            override fun onCapsuleClick(capsuleModel: CapsuleModel, position: Int) {

            }
        }

    }

    override fun getItemCount(): Int {
        return capsuleodels.size
    }

    fun update(modelList: ArrayList<CapsuleModel>) {
        capsuleodels = modelList
        notifyDataSetChanged()
    }

    init {
        this.context = context
        this.capsuleodels = capsuleModels
    }
}