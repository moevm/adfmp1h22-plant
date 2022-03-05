package com.artex.plants.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.artex.plants.R
import com.artex.plants.Shedule
import com.artex.plants.data.Message
import com.artex.plants.data.PlantListItem
import com.artex.plants.data.ScheduleItem


class SheduleAdapter(
    private var plantList: List<ScheduleItem>,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.chapter_item_layout,
                parent, false
            )
            return ViewHolderPlant(itemView)
        }else {
            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.msg,
                parent, false
            )
            return ViewHolderMsg(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val currentItem = plantList[position]
//        holder.plantName.text = currentItem.name
//        holder.plantComment.text = currentItem.comment
//
//        holder.card.setOnClickListener {
//            listener.onItemClick(0)
//        }
    }

    override fun getItemViewType(position: Int): Int {
        if (plantList[position] is PlantListItem) return 0
        return 1
    }

    override fun getItemCount(): Int {
        return plantList.size
    }

    class ViewHolderPlant(itemView: View): RecyclerView.ViewHolder(itemView) {
        var plantName: TextView
        var plantComment: TextView
        var card: CardView
        init {
            plantName = itemView.findViewById<TextView>(R.id.plant_name)
            plantComment = itemView.findViewById<TextView>(R.id.plant_comment)
            card = itemView.findViewById<CardView>(R.id.card)
        }
    }

    class ViewHolderMsg(itemView: View): RecyclerView.ViewHolder(itemView) {
        var msg: TextView
        init {
            msg = itemView.findViewById<TextView>(R.id.plant_name)
        }
    }

}