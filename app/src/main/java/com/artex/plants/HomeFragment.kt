package com.artex.plants

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artex.plants.adapters.ChapterAdapter
import com.artex.plants.data.Plant
import com.artex.plants.data.PlantListItem
import com.artex.plants.data.ScheduleMode
import com.artex.plants.data.Type
import com.artex.plants.viewmodels.PlantViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment(R.layout.fragment_home), ChapterAdapter.OnPlantClickListener {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ChapterAdapter
    private lateinit var plantViewModel: PlantViewModel
    private lateinit var localPlants: List<Plant>
    private lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        localPlants = listOf<Plant>()

        recycler = view.findViewById(R.id.recycler)
        searchView = view.findViewById(R.id.searchView)
        adapter = ChapterAdapter(this, arrayListOf<PlantListItem>())
        recycler.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                return true
            }

        })

        view.findViewById<FloatingActionButton>(R.id.addPlantBtn).setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddPlant()
            findNavController().navigate(action)
        }

        view.findViewById<FloatingActionButton>(R.id.scheduleBtn).setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToShedule(
                mode = ScheduleMode.GLOBAL,
                plant = Plant()
            )
            findNavController().navigate(action)
        }

        view.findViewById<FloatingActionButton>(R.id.careBtn).setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToNotificationsFragment()
            findNavController().navigate(action)
        }

        val activity: MainActivity = activity as MainActivity
        plantViewModel = activity.plantViewModel
        plantViewModel.allPlants.observe(activity) { plants ->
            plants.let {
                localPlants = it
                adapter.update(get(it))
            }
        }

        plantViewModel.allTasks.observe(activity) { tasks ->
            tasks.let {
//                localPlants = it
//                adapter.update(get(it))
            }
        }

    }

    override fun onItemClick(position: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToPlant(localPlants[position])
        findNavController().navigate(action)
    }

    fun get(plants: List<Plant>): List<PlantListItem> {
        val list = arrayListOf<PlantListItem>()
        for (plant in plants) {
            list.add(PlantListItem(name = plant.name, comment = plant.comment, type = Type.PLANT))
        }
        return list
    }

    fun filterList(searchText: String){
        val filteredList = arrayListOf<Plant>()
        for (item in localPlants){
            if (item.name.lowercase().contains(searchText.lowercase()) ||
                item.comment.lowercase().contains(searchText.lowercase())) {
                filteredList.add(item)
            }
        }

        adapter.update(get(filteredList))
    }

}