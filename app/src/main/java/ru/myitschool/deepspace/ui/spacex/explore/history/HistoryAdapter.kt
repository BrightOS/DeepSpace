package ru.myitschool.deepspace.ui.spacex.explore.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.deepspace.data.model.HistoryModel
import ru.myitschool.deepspace.databinding.HistoryItemBinding
import ru.myitschool.deepspace.utils.addSubstringAtIndex
import ru.myitschool.deepspace.utils.convertDateFromUnix
import ru.myitschool.deepspace.utils.getDayOfMonthSuffix
import java.util.*

class HistoryAdapter internal constructor(
    landModels: ArrayList<HistoryModel>,
) :
    RecyclerView.Adapter<HistoryViewHolder>() {
    private var historyModels: ArrayList<HistoryModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            HistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyModel: HistoryModel = historyModels[position]

        holder.binding.detailsHistory.text = historyModel.details
        holder.binding.historyArticleItem.text = historyModel.title

        val finalString = convertDateFromUnix(historyModel.event_date_utc)

        val calendar = GregorianCalendar()
        calendar.time = Date(historyModel.event_date_utc * 1000L)

        holder.binding.eventDateHistory.text = finalString.addSubstringAtIndex(
            getDayOfMonthSuffix(
                calendar.get(Calendar.DAY_OF_MONTH)
            ),
            finalString.indexOf('.')
        )

    }

    override fun getItemCount(): Int {
        return historyModels.size
    }

    fun update(modelList: ArrayList<HistoryModel>) {
        historyModels = modelList
        notifyDataSetChanged()
    }

    init {
        this.historyModels = landModels
    }
}