package com.artex.plants.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.artex.plants.R
import com.artex.plants.data.PlantListItem


class ChapterAdapter(
    private val listener: OnPlantClickListener,
    private var plantList: List<PlantListItem>,
) :
    RecyclerView.Adapter<ChapterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.chapter_item_layout,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = plantList[position]
        holder.plantName.text = currentItem.name
        holder.plantComment.text = currentItem.comment

        holder.card.setOnClickListener {
            listener.onItemClick(0)
        }
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    class ViewHolder( itemView: View): RecyclerView.ViewHolder(itemView) {
        var plantName: TextView
        var plantComment: TextView
        var card: CardView
        init {
            plantName = itemView.findViewById<TextView>(R.id.notification_action)
            plantComment = itemView.findViewById<TextView>(R.id.notification_comment)
            card = itemView.findViewById<CardView>(R.id.card)
        }
    }

    interface OnPlantClickListener {
        fun onItemClick(position: Int)
    }
}