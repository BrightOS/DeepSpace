package ru.myitschool.nasa_bootcamp.ui.spacex.explore.history

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.databinding.HistoryItemBinding
import ru.myitschool.nasa_bootcamp.databinding.LandPadItemBinding

class HistoryViewHolder (
    val binding: HistoryItemBinding,
private val context: Context
) : RecyclerView.ViewHolder(binding.root)