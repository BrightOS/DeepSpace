package ru.myitschool.nasa_bootcamp.ui.spacex.explore.launchland

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.databinding.LandPadItemBinding
import ru.myitschool.nasa_bootcamp.databinding.LaunchPadTemBinding

class LaunchHolder  (
    val binding: LaunchPadTemBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root)