package com.artex.plants

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.artex.plants.data.Plant
import com.artex.plants.viewmodels.PlantViewModel
import com.google.android.material.textfield.TextInputEditText
import java.util.*


class EditPlanFragment : Fragment(R.layout.edit_plant) {

    private lateinit var plantViewModel: PlantViewModel
    private lateinit var editComment: TextInputEditText
    private lateinit var editBtn: Button
    private lateinit var deleteBtn: Button
    private val args: EditPlanFragmentArgs by navArgs()
    private lateinit var plant: Plant

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plant = args.plant
        editComment = view.findViewById(R.id.edit_comment)
        deleteBtn = view.findViewById(R.id.deletePlantBtn)
        editBtn = view.findViewById(R.id.edit_comment_btn)

        editComment.setText(plant.comment)

        val activity: MainActivity = activity as MainActivity
        plantViewModel = activity.plantViewModel

        editBtn.setOnClickListener {
            plant.comment = editComment.text.toString()
            plantViewModel.update(plant)
        }

        if (args.isCreateTime){
            deleteBtn.visibility = View.GONE
        }

        deleteBtn.setOnClickListener {
            val id = plant.id
            lifecycleScope.launchWhenStarted {
                plantViewModel.deletePlantById(id)
                plantViewModel.deleteTasksByPlantId(id)
            }
            val action = EditPlanFragmentDirections.actionEditPlanFragmentToHomeFragment2()
            findNavController().navigate(action)
        }

    }

}