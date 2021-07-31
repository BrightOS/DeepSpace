package ru.myitschool.nasa_bootcamp.ui.spacex.explore.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.data.model.HistoryModel
import ru.myitschool.nasa_bootcamp.databinding.HistoryItemBinding
import java.util.ArrayList

class HistoryAdapter internal constructor(
    context: Context,
    landModels: ArrayList<HistoryModel>,

) :
    RecyclerView.Adapter<HistoryViewHolder>() {
    var context: Context
    var historyModels: ArrayList<HistoryModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            HistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            context
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyModel: HistoryModel = historyModels[position]



    }

    override fun getItemCount(): Int {
        return historyModels.size
    }

    fun update(modelList: ArrayList<HistoryModel>) {
        historyModels = modelList
        notifyDataSetChanged()
    }

    init {
        this.context = context
        this.historyModels = landModels
    }
}