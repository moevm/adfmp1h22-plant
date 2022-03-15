package com.artex.plants

import android.icu.text.CaseMap
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artex.plants.adapters.ScheduleAdapter
import com.artex.plants.data.*


class ScheduleFragment : Fragment(R.layout.shedule) {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ScheduleAdapter
    private lateinit var title: TextView
    private val args: ScheduleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title = view.findViewById(R.id.schedule_title)

        val list = arrayListOf<ScheduleItem>()
        if (args.mode == ScheduleMode.GLOBAL) {
            title.text = "Shared Schedule"
            list.add(Message("Today", Type.DAY))
            list.add(PlantListItem("Rose", "flowerpot with bus", Type.PLANT))
            list.add(Message("water", Type.ACTION))
            list.add(PlantListItem("Iris", "flowerpot with ball", Type.PLANT))
            list.add(Message("trim", Type.ACTION))
            list.add(Message("Tomorrow", Type.DAY))
            list.add(PlantListItem("Daisy", "flowerpot with sun", Type.PLANT))
            list.add(Message("feed", Type.ACTION))
            list.add(Message("9:00 get from hotbed", Type.ACTION))
            list.add(Message("21:00 put in hotbed", Type.ACTION))
        }
        if (args.mode == ScheduleMode.LOCAL) {
            title.text = "Schedule for Daisy"
            list.add(PlantListItem("Daisy", "flowerpot with sun", Type.PLANT))
            list.add(Message("feed", Type.ACTION))
            list.add(Message("9:00 get from hotbed", Type.ACTION))
            list.add(Message("21:00 put in hotbed", Type.ACTION))
        }


        recycler = view.findViewById(R.id.shedule_recycler)
        adapter = ScheduleAdapter(list)
        recycler.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager

    }

}