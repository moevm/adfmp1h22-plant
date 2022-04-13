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
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class NotificationsFragment : Fragment(R.layout.notifications), TaskAdapter.OnTaskClickListener {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: TaskAdapter
    private lateinit var plantViewModel: PlantViewModel
    private lateinit var modes: Array<String>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        modes = resources.getStringArray(R.array.plants_modes_array)

        val activity: MainActivity = activity as MainActivity
        plantViewModel = activity.plantViewModel

        val plants = plantViewModel.allPlants.value
        val oldTasks = plantViewModel.allTasks.value

        if (plants!== null && oldTasks!=null) {
            val newTasks = createTasks(oldTasks, plants)
            plantViewModel.deleteAllTasks()
            for (task in newTasks){
                plantViewModel.insertTask(task)
            }
        }

        /*var isTodayData = true
        if (tasks != null) {
            if (tasks.size > 0) {
                val sdf = SimpleDateFormat("dd.MM.yyyy")
                val todayDate = sdf.format(Date())
                for (item in tasks) {
                    if (!item.taskDate.equals(todayDate)) {
                        plantViewModel.deleteAllTasks()
                        isTodayData = false
                        break
                    }
                }
            } else {
                isTodayData = false
            }
        } else {
            isTodayData = false
        }*/


        /*if (isTodayData) {
//            list = tasks as ArrayList<Task>
        } else {
            val plants = plantViewModel.allPlants.value

//            if (plants != null && plants.size > 0) {
//                for (plantInList in plants){
//                    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
//                    val dateBefore: LocalDate = LocalDate.now()
//                    val dateAfter: LocalDate = LocalDate.parse(plantInList.createTime, formatter)
//                    val scheduleItem = getPlantListItem(plantInList, 0, dateBefore, dateAfter, formatter)
//                    if (scheduleItem.water) {
//                        plantViewModel.insertTask(Task(name = scheduleItem.name, comment = scheduleItem.comment, plantId = plantInList.id, action = "Water", isDone = false))
//                    }
//                    if (scheduleItem.trim) {
//                        plantViewModel.insertTask(Task(name = scheduleItem.name, comment = scheduleItem.comment, plantId = plantInList.id, action = "Trim", isDone = false))
//                    }
//                    if (scheduleItem.feed) {
//                        plantViewModel.insertTask(Task(name = scheduleItem.name, comment = scheduleItem.comment, plantId = plantInList.id, action = "Feed", isDone = false))
//                    }
//                }
//            }
        }*/

//        var list = arrayListOf<Task>()
        recycler = view.findViewById(R.id.notifications_adapter)
        adapter = TaskAdapter(this, arrayListOf<Task>())
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

    override fun onItemClick(task: Task) {
        plantViewModel.updateTask(task)
    }

    fun createTasks(oldTasks: List<Task>, plants: List<Plant>): ArrayList<Task> {
        val newTasks = arrayListOf<Task>()
        if (plants.size > 0) {
            for (plantInList in plants) {
                val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                val dateBefore: LocalDate = LocalDate.now()
                val dateAfter: LocalDate = LocalDate.parse(plantInList.createTime, formatter)
                val scheduleItem =
                    getPlantListItem(plantInList, 0, dateBefore, dateAfter, formatter)
                if (scheduleItem.water) {
                    newTasks.add(
                        Task(
                            name = scheduleItem.name,
                            comment = scheduleItem.comment,
                            plantId = plantInList.id,
                            action = "Water",
                            isDone = false
                        )
                    )
                }
                if (scheduleItem.trim) {
                    newTasks.add(
                        Task(
                            name = scheduleItem.name,
                            comment = scheduleItem.comment,
                            plantId = plantInList.id,
                            action = "Trim",
                            isDone = false
                        )
                    )
                }
                if (scheduleItem.feed) {
                    newTasks.add(
                        Task(
                            name = scheduleItem.name,
                            comment = scheduleItem.comment,
                            plantId = plantInList.id,
                            action = "Feed",
                            isDone = false
                        )
                    )
                }
            }
        }

        val map: MutableMap<String, Boolean> = mutableMapOf()

        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val todayDate = sdf.format(Date())

        for (task in oldTasks){
            if (task.taskDate.equals(todayDate)) {
                map.put(task.name + task.action + " " + task.plantId.toString(), task.isDone)
            }
        }

        for (task in newTasks){
            val item = map.get(task.name + task.action + " " + task.plantId.toString())
            if (item!= null){
                task.isDone = item
            }
        }

        return newTasks
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