package ru.myitschool.nasa_bootcamp.ui.spacex.explore.capsules

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.data.model.CapsuleModel
import ru.myitschool.nasa_bootcamp.data.model.CoreModel
import ru.myitschool.nasa_bootcamp.databinding.CapsuleItemBinding
import ru.myitschool.nasa_bootcamp.databinding.CoreItemBinding
import ru.myitschool.nasa_bootcamp.ui.spacex.explore.cores.CoresViewHolder
import ru.myitschool.nasa_bootcamp.utils.convertDateFromUnix
import java.util.ArrayList

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

        holder.binding.capsuleItemDescription.text = "${capsuleModel.details}"
        holder.binding.capsuleItemSerial.text = "Capsule : ${capsuleModel.capsule_serial}"
        holder.binding.capsuleItemLandings.text = "Number of landings : ${capsuleModel.landings}"
        if (capsuleModel.missions.size > 0)
            holder.binding.capsuleItemMissions.text = "Mission : ${capsuleModel.missions[0].name}"
        holder.binding.capsuleItemStatus.text = "Status : ${capsuleModel.status}"
        holder.binding.capsuleTypeItem.text = "Type : ${capsuleModel.type}"
        holder.binding.capsuleLaunchDate.text =
            "Date : ${convertDateFromUnix(capsuleModel.original_launch_unix)}"

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