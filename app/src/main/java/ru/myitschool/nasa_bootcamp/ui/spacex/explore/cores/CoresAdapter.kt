package ru.myitschool.nasa_bootcamp.ui.spacex.explore.cores

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.data.model.CoreModel
import ru.myitschool.nasa_bootcamp.data.model.DragonModel
import ru.myitschool.nasa_bootcamp.data.model.RoverModel
import ru.myitschool.nasa_bootcamp.databinding.CoreItemBinding
import ru.myitschool.nasa_bootcamp.databinding.DragonItemBinding
import ru.myitschool.nasa_bootcamp.ui.spacex.explore.dragons.DragonsViewHolder
import ru.myitschool.nasa_bootcamp.utils.convertDateFromUnix
import java.util.ArrayList

class CoresAdapter internal constructor(
    context: Context,
    coreModels: ArrayList<CoreModel>,
    // val navController: NavController
) :
    RecyclerView.Adapter<CoresViewHolder>() {
    var context: Context
    var coreModels: ArrayList<CoreModel>

    internal interface onCoreClickListener {
        fun onCoreClick(coreModel: CoreModel, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoresViewHolder {
        return CoresViewHolder(
            CoreItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context
        )
    }

    override fun onBindViewHolder(holder: CoresViewHolder, position: Int) {
        val coreModel: CoreModel = coreModels[position]

        holder.binding.coreItemBlocks.text = "Blocks : ${coreModel.block}"
        holder.binding.coreItemSerial.text = "Core : ${coreModel.core_serial}"
        holder.binding.coreItemMissions.text = "Mission : ${coreModel.missions}"
        holder.binding.coreItemReuseCount.text = "Reused : ${coreModel.reuse_count} times}"

        if(coreModel.status!=null)
        holder.binding.coreItemStatus.text = "Status : ${coreModel.status}"

        if (coreModel.details != null)
            holder.binding.coreItemDescription.text = "${coreModel.details}"

        holder.binding.coreItemWaterLanding.text =
            "Landed on water? : ${if (coreModel.water_landing) "Yes" else "No"}"
        holder.binding.originalLaunchDate.text =
            "Date : ${convertDateFromUnix(coreModel.original_launch_unix)}"

        val onDragonClickListener = object : onCoreClickListener {
            override fun onCoreClick(coreModel: CoreModel, position: Int) {

            }
        }

    }

    override fun getItemCount(): Int {
        return coreModels.size
    }

    fun update(modelList: ArrayList<CoreModel>) {
        coreModels = modelList
        notifyDataSetChanged()
    }

    init {
        this.context = context
        this.coreModels = coreModels
    }
}