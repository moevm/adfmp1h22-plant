package com.artex.plants

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artex.plants.adapters.PlantAddingAdapter
import com.artex.plants.data.Plant
import com.artex.plants.viewmodels.WordViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddPlantFragment : Fragment(R.layout.add_plant), PlantAddingAdapter.OnAddPlantClickListener {

    private lateinit var adapter: PlantAddingAdapter

    private lateinit var recycler: RecyclerView

    private lateinit var wordViewModel: WordViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query: Query = Firebase.firestore.collection("Plants")
        val options = FirestoreRecyclerOptions.Builder<Plant>()
            .setQuery(query, Plant::class.java)
            .build()

        recycler = view.findViewById(R.id.add_plant_recycler)
        adapter = PlantAddingAdapter(this, options)
        recycler.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager

        val activity: MainActivity = activity as MainActivity
        wordViewModel = activity.wordViewModel
    }

    override fun onItemClick(model: Plant) {
        Log.d("TAG", model.toString())
        wordViewModel.insert(model)
        val action = AddPlantFragmentDirections.actionAddPlantToPlant()
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}