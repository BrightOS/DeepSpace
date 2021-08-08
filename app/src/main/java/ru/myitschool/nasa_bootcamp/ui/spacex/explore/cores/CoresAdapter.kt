package ru.myitschool.nasa_bootcamp.ui.spacex.explore.cores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.data.model.CoreModel
import ru.myitschool.nasa_bootcamp.data.model.DragonModel
import ru.myitschool.nasa_bootcamp.data.model.RoverModel
import ru.myitschool.nasa_bootcamp.databinding.CoreItemBinding
import ru.myitschool.nasa_bootcamp.databinding.DragonItemBinding
import ru.myitschool.nasa_bootcamp.ui.spacex.explore.dragons.DragonsViewHolder
import ru.myitschool.nasa_bootcamp.utils.addSubstringAtIndex
import ru.myitschool.nasa_bootcamp.utils.capitalize
import ru.myitschool.nasa_bootcamp.utils.convertDateFromUnix
import ru.myitschool.nasa_bootcamp.utils.getDayOfMonthSuffix
import java.util.*

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

        holder.binding.coreItemBlocks.text = if (coreModel.block != null)
            "${coreModel.block}"
        else "None"

        holder.binding.coreItemSerialTitle.text = "Core:"
        holder.binding.coreItemMissionsTitle.text = "Mission:"
        holder.binding.coreItemWaterLandingTitle.text = "Landed on water?"
        holder.binding.coreItemStatusTitle.text = "Status:"
        holder.binding.coreItemBlocksTitle.text = "Blocks:"
        holder.binding.coreItemReusedCountTitle.text = "Reused:"

        holder.binding.coreItemSerial.text = coreModel.core_serial
        holder.binding.coreItemMissions.text = coreModel.missions[0].name
        coreModel.reuse_count.let {
            if (it == 1)
                holder.binding.coreItemReuseCount.text = "${coreModel.reuse_count} time"
            else
                holder.binding.coreItemReuseCount.text = "${coreModel.reuse_count} times"
        }

        if (coreModel.status != null)
            holder.binding.coreItemStatus.text = capitalize(coreModel.status)

        if (coreModel.details != null)
            holder.binding.coreItemDescription.text = "${coreModel.details}"
        else {
            holder.binding.coreItemDescription.visibility = View.GONE
            holder.binding.separator.visibility = View.GONE
        }

        holder.binding.coreItemWaterLanding.text =
            if (coreModel.water_landing)
                "Yes"
            else
                "No"

        val finalString = convertDateFromUnix(coreModel.original_launch_unix)

        val calendar = GregorianCalendar()
        calendar.time = Date(coreModel.original_launch_unix * 1000L)

        holder.binding.originalLaunchDate.text = finalString.addSubstringAtIndex(
            getDayOfMonthSuffix(
                calendar.get(Calendar.DAY_OF_MONTH)
            ),
            finalString.indexOf('.')
        )

        val onCoreClickListener = object : onCoreClickListener {
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