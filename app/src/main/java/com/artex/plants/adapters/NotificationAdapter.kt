package com.artex.plants.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artex.plants.R
import com.artex.plants.data.Notification


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
        val notification = notificationList[position]
        holder.action.text = notification.action
        holder.comment.text = notification.comment
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    class ViewHolder( itemView: View): RecyclerView.ViewHolder(itemView) {
        var action: TextView
        var comment: TextView
        var doneBtn: Button
        init {
            action = itemView.findViewById<TextView>(R.id.notification_action)
            comment = itemView.findViewById<TextView>(R.id.notification_comment)
            doneBtn = itemView.findViewById<Button>(R.id.done_btn)
        }
    }

}