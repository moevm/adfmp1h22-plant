package com.artex.plants

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.artex.plants.data.ScheduleMode


class PlantFragment : Fragment(R.layout.plant) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.plantSheduleBtn).setOnClickListener {
            val action = PlantFragmentDirections.actionPlantToShedule(mode = ScheduleMode.LOCAL)
            findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.carePlanBtn).setOnClickListener {
            val action = PlantFragmentDirections.actionPlantToCarePlan()
            findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.notificationsBtn).setOnClickListener {
            val action = PlantFragmentDirections.actionPlantToNotificationsFragment()
            findNavController().navigate(action)
        }
    }

}