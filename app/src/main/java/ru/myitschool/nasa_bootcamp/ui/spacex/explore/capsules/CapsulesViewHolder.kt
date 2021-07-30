package ru.myitschool.nasa_bootcamp.ui.spacex.explore.capsules

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import ru.myitschool.nasa_bootcamp.databinding.CapsuleItemBinding
import ru.myitschool.nasa_bootcamp.databinding.CoreItemBinding

class CapsulesViewHolder (
    val binding: CapsuleItemBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root)