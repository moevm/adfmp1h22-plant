package com.artex.plants

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.artex.plants.data.Plant
import com.artex.plants.viewmodels.PlantViewModel
import com.google.android.material.textfield.TextInputEditText
import java.util.*


class EditPlanFragment : Fragment(R.layout.edit_plant) {

    private lateinit var plantViewModel: PlantViewModel
    private lateinit var editComment: TextInputEditText
    private lateinit var editBtn: Button
    private val args: EditPlanFragmentArgs by navArgs()
    private lateinit var plant: Plant

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plant = args.plant
        editComment = view.findViewById(R.id.edit_comment)
        editComment.setText(plant.comment)
        editBtn = view.findViewById(R.id.edit_comment_btn)

        val activity: MainActivity = activity as MainActivity
        plantViewModel = activity.plantViewModel

        editBtn.setOnClickListener {
            plant.comment = editComment.text.toString()
            plantViewModel.update(plant)
        }
    }

}