package com.artex.plants

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.plant_item.*


class CarePlanFragment : Fragment(R.layout.care_plan), AdapterView.OnItemSelectedListener {

    private lateinit var waterSpinner: Spinner
    private lateinit var feedSpinner: Spinner
    private lateinit var trimSpinner: Spinner


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        waterSpinner = view.findViewById(R.id.water_spinner)
        feedSpinner = view.findViewById(R.id.feed_spinner)
        trimSpinner = view.findViewById(R.id.trim_spinner)


        val adapter = ArrayAdapter.createFromResource(
            view.context,
            R.array.plants_time_array, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        waterSpinner.adapter = adapter
        feedSpinner.adapter = adapter
        trimSpinner.adapter = adapter
        waterSpinner.setOnItemSelectedListener(this)
        feedSpinner.setOnItemSelectedListener(this)
        trimSpinner.setOnItemSelectedListener(this)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.d("TAG", p0.toString())
        when (p0!!.id) {
            R.id.water_spinner -> {
                Toast.makeText(context, "water", Toast.LENGTH_SHORT).show()
            }
            R.id.feed_spinner -> {
                Toast.makeText(context, "feed", Toast.LENGTH_SHORT).show()
            }
            R.id.trim_spinner -> {
                Toast.makeText(context, "trim", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}