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
import com.google.android.material.chip.Chip


class ScheduleAdapter(
    private var plantList: List<ScheduleItem>,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun inflateView(resource: Int, viewGroup: ViewGroup): View {
        return LayoutInflater.from(viewGroup.context).inflate(resource, viewGroup, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when {
            viewType == 0 -> ViewHolderPlant(inflateView(R.layout.schedule_item_layout, parent))
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
            if (!item.water) {
                holder.waterChip.visibility = View.GONE
            }
            if (!item.feed) {
                holder.feedChip.visibility = View.GONE
            }
            if (!item.trim) {
                holder.trimChip.visibility = View.GONE
            }
            if (item.isHotbedUse){
                holder.putInHotbedChip.visibility = View.GONE
                holder.getFromHotbedChip.visibility = View.GONE
            } else {
                holder.putInHotbedChip.text = "Put in hotbed: ${item.putInHotbed}"
                holder.getFromHotbedChip.text = "Get from hotbed: ${item.getFromHotbed}"
            }
            holder.date.text = item.date
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
        var date: TextView
        var card: CardView
        var waterChip: Chip
        var feedChip: Chip
        var trimChip: Chip
        var getFromHotbedChip: Chip
        var putInHotbedChip: Chip

        init {
            plantName = itemView.findViewById<TextView>(R.id.notification_action)
            plantComment = itemView.findViewById<TextView>(R.id.notification_comment)
            date = itemView.findViewById<TextView>(R.id.date)
            card = itemView.findViewById<CardView>(R.id.card)
            waterChip = itemView.findViewById(R.id.water_chip)
            feedChip = itemView.findViewById(R.id.feed_chip)
            trimChip = itemView.findViewById(R.id.trim_chip)
            getFromHotbedChip = itemView.findViewById(R.id.get_from_hotbed_chip)
            putInHotbedChip = itemView.findViewById(R.id.putin_hotbed_chip)
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