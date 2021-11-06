package ru.myitschool.deepspace.ui.spacex.explore.capsules

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.deepspace.data.model.CapsuleModel
import ru.myitschool.deepspace.databinding.CapsuleItemBinding
import ru.myitschool.deepspace.utils.addSubstringAtIndex
import ru.myitschool.deepspace.utils.capitalize
import ru.myitschool.deepspace.utils.convertDateFromUnix
import ru.myitschool.deepspace.utils.getDayOfMonthSuffix
import java.util.*

/*
 * @author Yana Glad
 */
class CapsulesAdapter internal constructor(
    capsuleModels: ArrayList<CapsuleModel>,
) :
    RecyclerView.Adapter<CapsulesViewHolder>() {
    private var capsuleModels: ArrayList<CapsuleModel>

    internal interface OnCapsuleClickListener {
        fun onCapsuleClick(capsuleModel: CapsuleModel, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapsulesViewHolder {
        return CapsulesViewHolder(
            CapsuleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CapsulesViewHolder, position: Int) {
        val capsuleModel: CapsuleModel = capsuleModels[position]

        with(holder.binding) {
            if (capsuleModel.details != null)
                capsuleItemDescription.text = "${capsuleModel.details}"
            else {
                capsuleItemDescription.visibility = View.GONE
                separator.visibility = View.GONE
            }

            capsuleItemSerialTitle.text = "Capsule:"
            capsuleTypeItemTitle.text = "Type:"
            capsuleItemStatusTitle.text = "Status:"
            capsuleItemLandingsTitle.text = "Number of landings:"
            capsuleItemMissionsTitle.text = "Mission:"

            capsuleItemSerial.text = capsuleModel.capsule_serial
            capsuleItemLandings.text = "${capsuleModel.landings}"
            if (capsuleModel.missions.isNotEmpty())
                capsuleItemMissions.text = capsuleModel.missions[0].name
            capsuleItemStatus.text = capitalize(capsuleModel.status)
            capsuleTypeItem.text = capsuleModel.type

            setupDateData(capsuleModel)
        }

        val onCapsuleClickListener = object : OnCapsuleClickListener {
            override fun onCapsuleClick(capsuleModel: CapsuleModel, position: Int) {

            }
        }
    }

    private fun CapsuleItemBinding.setupDateData(capsuleModel: CapsuleModel) {
        val finalString = convertDateFromUnix(capsuleModel.original_launch_unix)
        val calendar = GregorianCalendar()
        calendar.time = Date(capsuleModel.original_launch_unix * 1000L)

        capsuleLaunchDate.text = finalString.addSubstringAtIndex(
            getDayOfMonthSuffix(
                calendar.get(Calendar.DAY_OF_MONTH)
            ),
            finalString.indexOf('.')
        )
    }

    override fun getItemCount(): Int {
        return capsuleModels.size
    }

    fun update(modelList: ArrayList<CapsuleModel>) {
        capsuleModels = modelList
        notifyDataSetChanged()
    }

    init {
        this.capsuleModels = capsuleModels
    }
}
