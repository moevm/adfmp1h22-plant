package com.artex.plants.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.artex.plants.R
import com.artex.plants.data.Message
import com.artex.plants.data.PlantListItem
import com.artex.plants.data.ScheduleItem
import com.artex.plants.data.Type


class ScheduleAdapter(
    private var plantList: List<ScheduleItem>,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun inflateView(resource: Int, viewGroup: ViewGroup): View {
        return LayoutInflater.from(viewGroup.context).inflate(resource, viewGroup, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when {
            viewType == 0 -> ViewHolderPlant(inflateView(R.layout.chapter_item_layout, parent))
            viewType == 1 -> ViewHolderMsg(inflateView(R.layout.day_item_layout, parent))
            viewType == 2 -> ViewHolderAction(inflateView(R.layout.action, parent))
            else -> null!!
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = plantList[position]
        if (item is PlantListItem) {
            val holder = holder as ViewHolderPlant
            holder.plantName.text = item.name
            holder.plantComment.text = item.comment
        }
        if (item is Message && item.type == Type.DAY) {
            val holder = holder as ViewHolderMsg
            holder.day.text = item.text
        }
        if (item is Message && item.type == Type.ACTION) {
            val holder = holder as ViewHolderAction
            holder.action.text = item.text
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = plantList[position]
        return when {
            item.type == Type.PLANT -> 0
            item.type == Type.DAY -> 1
            item.type == Type.ACTION -> 2
            else -> -1
        }
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    class ViewHolderPlant(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var plantName: TextView
        var plantComment: TextView
        var card: CardView

        init {
            plantName = itemView.findViewById<TextView>(R.id.plant_name)
            plantComment = itemView.findViewById<TextView>(R.id.plant_comment)
            card = itemView.findViewById<CardView>(R.id.card)
        }
    }

    class ViewHolderMsg(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var day: TextView
        init { day = itemView.findViewById<TextView>(R.id.day) }
    }

    class ViewHolderAction(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var action: TextView
        init { action = itemView.findViewById<TextView>(R.id.action) }
    }

}