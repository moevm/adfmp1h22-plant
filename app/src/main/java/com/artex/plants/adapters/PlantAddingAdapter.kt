package com.artex.plants.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.artex.plants.R
import com.artex.plants.data.Plant
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class PlantAddingAdapter(
    private val listener: OnAddPlantClickListener,
    options: FirestoreRecyclerOptions<Plant>
) :
    FirestoreRecyclerAdapter<Plant, PlantAddingAdapter.ViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.plant_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var card: CardView

        init {
            name = itemView.findViewById<TextView>(R.id.notification_action)
            card = itemView.findViewById<CardView>(R.id.card)
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Plant) {
        holder.name.text = model.name

        holder.card.setOnClickListener {
            listener.onItemClick(model)
        }

    }

    interface OnAddPlantClickListener {
        fun onItemClick(model: Plant)
    }

}