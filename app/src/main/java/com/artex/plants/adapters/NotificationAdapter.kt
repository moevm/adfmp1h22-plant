package com.artex.plants.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.artex.plants.R
import com.artex.plants.data.Notification
import com.artex.plants.data.PlantListItem


class NotificationAdapter(
//    private val listener: OnPlantClickListener,
    private var notificationList: List<Notification>,
) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.notifications_list_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val currentItem = notificationList[position]
//        holder.plantName.text = currentItem.name
//        holder.plantComment.text = currentItem.comment
//
//        holder.card.setOnClickListener {
//            listener.onItemClick(0)
//        }
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    class ViewHolder( itemView: View): RecyclerView.ViewHolder(itemView) {
//        var plantName: TextView
//        var plantComment: TextView
//        var card: CardView
//        init {
//            plantName = itemView.findViewById<TextView>(R.id.notification_action)
//            plantComment = itemView.findViewById<TextView>(R.id.notification_comment)
//            card = itemView.findViewById<CardView>(R.id.card)
//        }
    }

//    interface OnPlantClickListener {
//        fun onItemClick(position: Int)
//    }
}