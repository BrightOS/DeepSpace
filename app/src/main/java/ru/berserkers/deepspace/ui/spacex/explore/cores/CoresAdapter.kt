package ru.berserkers.deepspace.ui.spacex.explore.cores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.berserkers.deepspace.R
import ru.berserkers.deepspace.data.model.CoreModel
import ru.berserkers.deepspace.databinding.CoreItemBinding
import ru.berserkers.deepspace.utils.addSubstringAtIndex
import ru.berserkers.deepspace.utils.capitalize
import ru.berserkers.deepspace.utils.convertDateFromUnix
import ru.berserkers.deepspace.utils.getDayOfMonthSuffix
import java.util.*

class CoresAdapter internal constructor(
    coreModels: ArrayList<CoreModel>,
) :
    RecyclerView.Adapter<CoresViewHolder>() {
    private var coreModels: ArrayList<CoreModel>

    internal interface OnCoreClickListener {
        fun onCoreClick(coreModel: CoreModel, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoresViewHolder {
        return CoresViewHolder(
            CoreItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CoresViewHolder, position: Int) {
        val coreModel: CoreModel = coreModels[position]

        with(holder.binding) {
            coreItemBlocks.text = if (coreModel.block != null)
                "${coreModel.block}"
            else "None"

            holder.itemView.context.also {
            coreItemSerialTitle.text = it.getString(R.string.core)
            coreItemMissionsTitle.text = it.getString(R.string.mission)
            coreItemWaterLandingTitle.text = it.getString(R.string.landedn_on_water)
            coreItemStatusTitle.text = it.getString(R.string.status)
            coreItemBlocksTitle.text = it.getString(R.string.blocks)
            coreItemReusedCountTitle.text = it.getString(R.string.reused)
            }

            coreItemSerial.text = coreModel.core_serial
            coreItemMissions.text = coreModel.missions[0].name
            coreModel.reuse_count.let {
                if (it == 1)
                    coreItemReuseCount.text = "${coreModel.reuse_count} time"
                else
                    coreItemReuseCount.text = "${coreModel.reuse_count} times"
            }

            if (coreModel.status != null)
                coreItemStatus.text = capitalize(coreModel.status)

            if (coreModel.details != null)
                coreItemDescription.text = "${coreModel.details}"
            else {
                coreItemDescription.visibility = View.GONE
                separator.visibility = View.GONE
            }

            coreItemWaterLanding.text =
                if (coreModel.water_landing)
                    "Yes"
                else
                    "No"

            val finalString = convertDateFromUnix(coreModel.original_launch_unix)

            val calendar = GregorianCalendar()
            calendar.time = Date(coreModel.original_launch_unix * 1000L)

            originalLaunchDate.text = finalString.addSubstringAtIndex(
                getDayOfMonthSuffix(
                    calendar.get(Calendar.DAY_OF_MONTH)
                ),
                finalString.indexOf('.')
            )
        }
        val onCoreClickListener = object : OnCoreClickListener {
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
        this.coreModels = coreModels
    }
}
