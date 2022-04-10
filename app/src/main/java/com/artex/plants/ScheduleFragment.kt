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
            list.add(Message("Today", Type.DAY))
            list.add(PlantListItem("Rose", "flowerpot with bus", type = Type.PLANT))
            list.add(Message("water", Type.ACTION))
            list.add(PlantListItem("Iris", "flowerpot with ball", type = Type.PLANT))
            list.add(Message("trim", Type.ACTION))
            list.add(Message("Tomorrow", Type.DAY))
            list.add(PlantListItem("Daisy", "flowerpot with sun", type = Type.PLANT))
            list.add(Message("feed", Type.ACTION))
            list.add(Message("9:00 get from hotbed", Type.ACTION))
            list.add(Message("21:00 put in hotbed", Type.ACTION))
        }
        if (args.mode == ScheduleMode.LOCAL) {
            val plant = args.plant
            title.text = "Weekly schedule for ${plant.name}"

            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            var dateBefore: LocalDate = LocalDate.now()
            val dateAfter: LocalDate = LocalDate.parse(plant.createTime, formatter)
            for (i in 0..6) {
                dateBefore = dateBefore.plusDays(i.toLong())
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


                val plantListItem = PlantListItem(
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
                list.add(plantListItem)
//                Log.d("TAG", "Water " + (modes.size - getPosition(plant.water, modes)).toString())
//                Log.d("TAG", "Feed " + (modes.size - getPosition(plant.feed, modes)).toString())
//                Log.d("TAG", "Trim " + (modes.size - getPosition(plant.trim, modes)).toString())
//                Log.d("TAG", "Days " + (daysBetween + 1).toString())
//                Log.d("TAG", plantListItem.toString())
//                Log.d("TAG", daysBetween.toString())
            }
        }
        recycler = view.findViewById(R.id.shedule_recycler)
        adapter = ScheduleAdapter(list)
        recycler.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager

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