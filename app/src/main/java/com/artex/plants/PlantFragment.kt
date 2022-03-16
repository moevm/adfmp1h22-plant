package com.artex.plants

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.artex.plants.data.Plant
import com.artex.plants.data.ScheduleMode


class PlantFragment : Fragment(R.layout.plant) {

    lateinit var topic: TextView
    lateinit var comment: TextView
    lateinit var date: TextView
    private val args: PlantFragmentArgs by navArgs()
    lateinit var plant: Plant

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plant = args.plant

        topic = view.findViewById(R.id.plantTopic)
        comment = view.findViewById(R.id.plantComment)
        date = view.findViewById(R.id.plantDate)
        topic.text = plant.name
        comment.text = plant.comment
        date.text = plant.createTime

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