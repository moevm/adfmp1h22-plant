package com.artex.plants

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artex.plants.adapters.ChapterAdapter
import com.artex.plants.data.*
import com.artex.plants.viewmodels.PlantViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator


class HomeFragment : Fragment(R.layout.fragment_home), ChapterAdapter.OnPlantClickListener {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ChapterAdapter
    private lateinit var plantViewModel: PlantViewModel
    private lateinit var localPlants: List<Plant>
    private lateinit var searchView: SearchView
    private var sortType: SortType = SortType.DEFAULT

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
                filterAndSortList(newText)
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

        view.findViewById<ImageView>(R.id.sortBtn).setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditSortFragment()
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

        val navController = findNavController();
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("sortKey")
            ?.observe(
                viewLifecycleOwner
            ) { result ->
                when (result) {
                    "sortNameAscend" -> {
                        sortType = SortType.SORT_NAME_ASCEND
                        filterAndSortList("")
                    }
                    "sortNameDescend" -> {
                        sortType = SortType.SORT_NAME_DESCEND
                        filterAndSortList("")
                    }
                    "sortDateAscend" -> {
                        sortType = SortType.SORT_DATE_ASCEND
                        filterAndSortList("")
                    }
                    "sortDateDescend" -> {
                        sortType = SortType.SORT_DATE_DESCEND
                        filterAndSortList("")
                    }
                }
            }

    }

    override fun onItemClick(id: Int) {
        for (plant in localPlants){
            if (plant.id == id){
                val action = HomeFragmentDirections.actionHomeFragmentToPlant(plant)
                findNavController().navigate(action)
                break
            }
        }
    }

    fun get(plants: List<Plant>): List<PlantListItem> {
        val list = arrayListOf<PlantListItem>()
        for (plant in plants) {
            list.add(PlantListItem(id = plant.id, name = plant.name, comment = plant.comment, type = Type.PLANT))
        }
        return list
    }

    fun filterAndSortList(searchText: String) {
        val filteredList = arrayListOf<Plant>()

        val copyLocalPlants = localPlants.toMutableList()

        when(sortType){
            SortType.SORT_NAME_ASCEND -> {
                copyLocalPlants.sortBy { it.name }
            }
            SortType.SORT_NAME_DESCEND -> {
                copyLocalPlants.sortByDescending { it.name }
            }
            SortType.SORT_DATE_ASCEND -> {
                copyLocalPlants.sortWith(ComparePlantByDates)
            }
            SortType.SORT_DATE_DESCEND -> {
                copyLocalPlants.sortWith(ComparePlantByDates)
                copyLocalPlants.reverse()
            }
            SortType.DEFAULT -> {}
        }

        for (item in copyLocalPlants) {
            if (item.name.lowercase().contains(searchText.lowercase()) ||
                item.comment.lowercase().contains(searchText.lowercase())
            ) {
                filteredList.add(item)
            }
        }

        adapter.update(get(filteredList))
    }

    class ComparePlantByDates {
        companion object : Comparator<Plant> {
            override fun compare(a: Plant, b: Plant): Int {

                val formatter = SimpleDateFormat("dd.MM.yyyy")
                val dateA: Date = formatter.parse(a.createTime)
                val dateB: Date = formatter.parse(b.createTime)

                return when {
                    dateA.year != dateB.year -> dateA.year - dateB.year
                    dateA.month != dateB.month -> dateA.month - dateB.month
                    else -> dateA.day - dateB.day
                }

            }
        }
    }

}