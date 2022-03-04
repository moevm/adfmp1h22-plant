package com.artex.plants

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artex.plants.adapters.ChapterAdapter
import com.artex.plants.adapters.PlantAddingAdapter
import com.artex.plants.data.PlantItem
import com.artex.plants.data.PlantListItem
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class AddPlant : Fragment(R.layout.add_plant), PlantAddingAdapter.OnAddPlantClickListener {

    private lateinit var adapter: PlantAddingAdapter

    private lateinit var recycler: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query: Query = Firebase.firestore.collection("Plants")
        val options = FirestoreRecyclerOptions.Builder<PlantItem>()
            .setQuery(query, PlantItem::class.java)
            .build()

        recycler = view.findViewById(R.id.add_plant_recycler)
        adapter = PlantAddingAdapter(this, options)
        recycler.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager
    }

    override fun onItemClick() {

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