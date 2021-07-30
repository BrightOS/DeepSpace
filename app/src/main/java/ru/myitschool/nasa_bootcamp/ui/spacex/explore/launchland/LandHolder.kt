package ru.myitschool.nasa_bootcamp.ui.spacex.explore.launchland

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.databinding.DragonItemBinding
import ru.myitschool.nasa_bootcamp.databinding.FragmentMapsBinding
import ru.myitschool.nasa_bootcamp.databinding.LandPadItemBinding

class LandHolder (
    val binding: LandPadItemBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root)