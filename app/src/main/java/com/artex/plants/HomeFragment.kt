package com.artex.plants

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artex.plants.adapters.ChapterAdapter
import com.artex.plants.data.PlantListItem
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment(R.layout.fragment_home), ChapterAdapter.OnPlantClickListener {

    private lateinit var recycler: RecyclerView
    private lateinit var goalAdapter: ChapterAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = arrayListOf<PlantListItem>()
        list.add(PlantListItem("Daisy", "flowerpot with sun"))
        list.add(PlantListItem("Rose", "flowerpot with bus"))
        list.add(PlantListItem("Iris", "flowerpot with ball"))
        list.add(PlantListItem("Narcissus", "flowerpot with car"))



        recycler = view.findViewById(R.id.recycler)
        goalAdapter = ChapterAdapter(this, list)
        recycler.adapter = goalAdapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager

        view.findViewById<FloatingActionButton>(R.id.addPlantBtn).setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddPlant()
            findNavController().navigate(action)
        }

        view.findViewById<FloatingActionButton>(R.id.scheduleBtn).setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToShedule()
            findNavController().navigate(action)
        }

    }

    override fun onItemClick(position: Int) {
            val action = HomeFragmentDirections.actionHomeFragmentToPlant()
            findNavController().navigate(action)
    }

}