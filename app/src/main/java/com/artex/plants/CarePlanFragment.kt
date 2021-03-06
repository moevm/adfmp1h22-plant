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
import java.util.*


class CarePlanFragment : Fragment(R.layout.care_plan), AdapterView.OnItemSelectedListener {

    private lateinit var waterSpinner: Spinner
    private lateinit var feedSpinner: Spinner
    private lateinit var trimSpinner: Spinner
    private lateinit var timeFromHotbed: TextView
    private lateinit var timeInHotbed: TextView
    private lateinit var plantViewModel: PlantViewModel
    private val args: PlantFragmentArgs by navArgs()
    private lateinit var plant: Plant
    private lateinit var modes: Array<String>
    private lateinit var checkBox: CheckBox


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        plant = args.plant

        waterSpinner = view.findViewById(R.id.water_spinner)
        feedSpinner = view.findViewById(R.id.feed_spinner)
        trimSpinner = view.findViewById(R.id.trim_spinner)

        timeFromHotbed = view.findViewById(R.id.time_from_hotbed)
        timeInHotbed = view.findViewById(R.id.time_in_hotbed)

        checkBox = view.findViewById(R.id.isHotbedCheckBox)

        modes = resources.getStringArray(R.array.plants_modes_array)

        timeFromHotbed.text = plant.getFromHotbed
        timeInHotbed.text = plant.putInHotbed

        val activity: MainActivity = activity as MainActivity
        plantViewModel = activity.plantViewModel

        val adapter = ArrayAdapter.createFromResource(
            view.context,
            R.array.plants_modes_array, android.R.layout.simple_spinner_item
        )

        if (!plant.isHotbedUse){
            checkBox.isChecked = true
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        waterSpinner.adapter = adapter
        feedSpinner.adapter = adapter
        trimSpinner.adapter = adapter
        waterSpinner.setOnItemSelectedListener(this)
        feedSpinner.setOnItemSelectedListener(this)
        trimSpinner.setOnItemSelectedListener(this)

        waterSpinner.setSelection(getPosition(plant.water, modes))
        feedSpinner.setSelection(getPosition(plant.feed, modes))
        trimSpinner.setSelection(getPosition(plant.trim, modes))

        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)

        timeInHotbed.setOnClickListener {
            TimePickerDialog(context, object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    val time = getTimeText(hourOfDay, minute)
                    timeInHotbed.text = time
                    plant.putInHotbed = time
                    plantViewModel.update(plant)
                }
            }, hour, minute, false).show()
        }

        timeFromHotbed.setOnClickListener {
            TimePickerDialog(context, object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    val time = getTimeText(hourOfDay, minute)
                    timeFromHotbed.text = time
                    plant.getFromHotbed = time
                    plantViewModel.update(plant)
                }
            }, hour, minute, false).show()
        }

        checkBox.setOnCheckedChangeListener { compoundButton, isChecked ->
            plant.isHotbedUse = !isChecked
            plantViewModel.update(plant)
        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

        when (p0!!.id) {
            R.id.water_spinner -> {
                plant.water = modes[position]
                plantViewModel.update(plant)
            }
            R.id.feed_spinner -> {
                plant.feed = modes[position]
                plantViewModel.update(plant)
            }
            R.id.trim_spinner -> {
                plant.trim = modes[position]
                plantViewModel.update(plant)
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    fun getTimeText(hourOfDay: Int, minute: Int): String {
        if (minute < 10) {
            return "${hourOfDay}:0${minute}"
        } else {
            return "${hourOfDay}:${minute}"
        }
    }

    fun getPosition(mode: String, modes: Array<String>): Int {
        for (i in 0..modes.size - 1) {
            if (mode == modes[i]) {
                return i
            }
        }
        return 0
    }
}