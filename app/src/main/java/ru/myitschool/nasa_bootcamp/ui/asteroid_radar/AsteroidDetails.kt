package ru.myitschool.nasa_bootcamp.ui.asteroid_radar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.myitschool.nasa_bootcamp.R


class AsteroidDetails : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val absolute_magnitude = it.getDouble("absolute_magnitude")
            val estimatedDiameter = it.getDouble("estimatedDiameter")
            val relativeVelocity = it.getDouble("relativeVelocity")

         }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asteroid_details, container, false)
    }


}