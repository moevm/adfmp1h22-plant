package com.artex.plants

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Plant : Fragment(R.layout.plant) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.plantSheduleBtn).setOnClickListener {
            val action = PlantDirections.actionPlantToShedule()
            findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.carePlanBtn).setOnClickListener {
            val action = PlantDirections.actionPlantToCarePlan()
            findNavController().navigate(action)
        }
    }

}