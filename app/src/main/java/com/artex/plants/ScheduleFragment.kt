package com.artex.plants

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artex.plants.adapters.ScheduleAdapter
import com.artex.plants.data.*
import com.artex.plants.viewmodels.PlantViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class ScheduleFragment : Fragment(R.layout.shedule) {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ScheduleAdapter
    private lateinit var title: TextView
    private lateinit var modes: Array<String>
    private val args: ScheduleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = view.findViewById(R.id.schedule_title)
        modes = resources.getStringArray(R.array.plants_modes_array)

        val list = arrayListOf<ScheduleItem>()
        if (args.mode == ScheduleMode.GLOBAL) {
            title.text = "Shared Schedule"

            val activity: MainActivity = activity as MainActivity
            val plantViewModel: PlantViewModel = activity.plantViewModel
            val plants = plantViewModel.allPlants.value

            if (plants != null && plants.size > 0) {
                list.add(Message("Today", Type.DAY))
                for (plantInList in plants){
                    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    val dateBefore: LocalDate = LocalDate.now()
                    val dateAfter: LocalDate = LocalDate.parse(plantInList.createTime, formatter)
                    list.add(getPlantListItem(plantInList, 0, dateBefore, dateAfter, formatter))
                }
            }

            if (plants != null && plants.size > 0) {
                list.add(Message("Tomorrow", Type.DAY))
                for (plantInList in plants){
                    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    val dateBefore: LocalDate = LocalDate.now()
                    val dateAfter: LocalDate = LocalDate.parse(plantInList.createTime, formatter)
                    list.add(getPlantListItem(plantInList, 1, dateBefore, dateAfter, formatter))
                }
            }

        }
        if (args.mode == ScheduleMode.LOCAL) {
            val plant = args.plant
            title.text = "Weekly schedule for ${plant.name}"

            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            var dateBefore: LocalDate = LocalDate.now()
            val dateAfter: LocalDate = LocalDate.parse(plant.createTime, formatter)
            for (i in 0..6) {
                if (i != 0) dateBefore = dateBefore.plusDays(1)
                list.add(getPlantListItem(plant, i, dateBefore, dateAfter, formatter))
            }
        }
        recycler = view.findViewById(R.id.shedule_recycler)
        adapter = ScheduleAdapter(list)
        recycler.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager
    }

    fun getPlantListItem(
        plant: Plant,
        i: Int,
        dateBefore: LocalDate,
        dateAfter: LocalDate,
        formatter: DateTimeFormatter
    ): PlantListItem {
        val daysBetween: Long = ChronoUnit.DAYS.between(dateAfter, dateBefore)

        val waterPosition = modes.size - getPosition(plant.water, modes)
        val water = waterPosition != 0 && ((daysBetween).toInt() % waterPosition) == 0

        val feedPosition = modes.size - getPosition(plant.feed, modes)
        val feed = feedPosition != 0 && ((daysBetween).toInt() % feedPosition) == 0

        val trimPosition = modes.size - getPosition(plant.trim, modes)
        val trim = trimPosition != 0 && ((daysBetween).toInt() % trimPosition) == 0

        var date = dateBefore.format(formatter)

        if (i == 0) date = "Today " + date
        if (i == 1) date = "Tommorow " + date

        return PlantListItem(
            plant.name,
            plant.comment,
            water = water,
            feed = feed,
            trim = trim,
            getFromHotbed = plant.getFromHotbed,
            putInHotbed = plant.putInHotbed,
            isHotbedUse = plant.isHotbedUse,
            date = date,
            type = Type.PLANT
        )
    }

    fun getPosition(mode: String, modes: Array<String>): Int {
        for (i in 0..modes.size - 1) {
            if (mode == modes[i]) {
                return i + 1
            }
        }
        return 1
    }

}