package com.artex.plants

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artex.plants.adapters.TaskAdapter
import com.artex.plants.data.*
import com.artex.plants.viewmodels.PlantViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


class NotificationsFragment : Fragment(R.layout.notifications) {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var plantViewModel: PlantViewModel
    private lateinit var modes: Array<String>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        modes = resources.getStringArray(R.array.plants_modes_array)

        val activity: MainActivity = activity as MainActivity
        plantViewModel = activity.plantViewModel

        val taskList = plantViewModel.allTasks.value
        var isTodayData = true
        if (taskList != null) {
            if (taskList.size > 0) {
                val sdf = SimpleDateFormat("dd.MM.yyyy")
                val todayDate = sdf.format(Date())
                for (item in taskList) {
                    if (!item.taskDate.equals(todayDate)) {
                        isTodayData = false
                        break
                    }
                }
            } else {
                isTodayData = false
            }
        } else {
            isTodayData = false
        }

        var list = arrayListOf<Task>()
        if (isTodayData) {
            list = taskList as ArrayList<Task>
        } else {
            val plants = plantViewModel.allPlants.value

            if (plants != null && plants.size > 0) {
                for (plantInList in plants){
                    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    val dateBefore: LocalDate = LocalDate.now()
                    val dateAfter: LocalDate = LocalDate.parse(plantInList.createTime, formatter)
                    val scheduleItem = getPlantListItem(plantInList, 0, dateBefore, dateAfter, formatter)
                    if (scheduleItem.water) {
                        plantViewModel.insertTask(Task(name = scheduleItem.name, comment = scheduleItem.comment, action = "Water", isDone = false))
                    }
                    if (scheduleItem.trim) {
                        plantViewModel.insertTask(Task(name = scheduleItem.name, comment = scheduleItem.comment, action = "Trim", isDone = false))
                    }
                    if (scheduleItem.feed) {
                        plantViewModel.insertTask(Task(name = scheduleItem.name, comment = scheduleItem.comment, action = "Feed", isDone = false))
                    }
                }
            }
        }


//        plantViewModel.allPlants.observe(activity) { plants ->
//            plants.let {
//                localPlants = it
//                adapter.update(get(it))
//            }
//        }

//        val list2 = arrayListOf<Notification>()
//        list.add(Notification("Water sunflowe","greenhouse #2", false))
//        list.add(Notification("Trim daisy","flowerpot with sun", false))


        recycler = view.findViewById(R.id.notifications_adapter)
        adapter = TaskAdapter(list)
        recycler.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager

        view.findViewById<Button>(R.id.go_home_btn).setOnClickListener {
            val action = NotificationsFragmentDirections.actionNotificationsFragmentToHomeFragment()
            findNavController().navigate(action)
        }

        plantViewModel.allTasks.observe(activity) { tasks ->
            tasks.let {
//                localPlants = it
                adapter.update(it)
            }
        }

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