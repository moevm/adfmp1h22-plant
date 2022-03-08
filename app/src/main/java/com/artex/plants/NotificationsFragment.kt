package com.artex.plants

import android.app.NotificationChannel
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artex.plants.adapters.ChapterAdapter
import com.artex.plants.adapters.NotificationAdapter
import com.artex.plants.adapters.ScheduleAdapter
import com.artex.plants.data.*
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NotificationsFragment : Fragment(R.layout.notifications) {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: NotificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = arrayListOf<Notification>()
        list.add(Notification("1","2", false))
        list.add(Notification("1","2", false))
        list.add(Notification("1","2", false))


//        if (args.mode == ScheduleMode.GLOBAL) {
//            list.add(Message("Today", Type.DAY))
//            list.add(PlantListItem("Rose", "flowerpot with bus", Type.PLANT))
//            list.add(Message("water", Type.ACTION))
//            list.add(PlantListItem("Iris", "flowerpot with ball", Type.PLANT))
//            list.add(Message("trim", Type.ACTION))
//            list.add(Message("Tomorrow", Type.DAY))
//            list.add(PlantListItem("Daisy", "flowerpot with sun", Type.PLANT))
//            list.add(Message("feed", Type.ACTION))
//            list.add(Message("9:00 get from hotbed", Type.ACTION))
//            list.add(Message("21:00 put in hotbed", Type.ACTION))
//        }
//        if (args.mode == ScheduleMode.LOCAL) {
//            list.add(PlantListItem("Daisy", "flowerpot with sun", Type.PLANT))
//            list.add(Message("feed", Type.ACTION))
//            list.add(Message("9:00 get from hotbed", Type.ACTION))
//            list.add(Message("21:00 put in hotbed", Type.ACTION))
//        }


        recycler = view.findViewById(R.id.notifications_adapter)
        adapter = NotificationAdapter(list)
        recycler.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager

//        view.findViewById<Button>(R.id.plantSheduleBtn).setOnClickListener {
//            val action = PlantFragmentDirections.actionPlantToShedule(mode = ScheduleMode.LOCAL)
//            findNavController().navigate(action)
//        }
//
//        view.findViewById<Button>(R.id.carePlanBtn).setOnClickListener {
//            val action = PlantFragmentDirections.actionPlantToCarePlan()
//            findNavController().navigate(action)
//        }
    }

}