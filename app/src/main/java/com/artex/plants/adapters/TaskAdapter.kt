package com.artex.plants.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artex.plants.R
import com.artex.plants.data.Task


class TaskAdapter(
    private val listener: OnTaskClickListener,
    private var taskList: List<Task>,
) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.tasks_list_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = taskList[position]
        holder.action.text = task.action + " " + task.name
        holder.comment.text = task.comment
        if (task.isDone) {
            holder.doneBtn.setBackgroundColor(Color.GREEN)
        } else {
            holder.doneBtn.setBackgroundColor(Color.RED)
        }
        holder.doneBtn.setOnClickListener {
            task.isDone = !task.isDone
            listener.onItemClick(task)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var action: TextView
        var comment: TextView
        var doneBtn: Button

        init {
            action = itemView.findViewById<TextView>(R.id.notification_action)
            comment = itemView.findViewById<TextView>(R.id.notification_comment)
            doneBtn = itemView.findViewById<Button>(R.id.done_btn)
        }
    }

    fun update(newTaskList: List<Task>) {
        taskList = newTaskList
        notifyDataSetChanged()
    }

    interface OnTaskClickListener {
        fun onItemClick(task: Task)
    }

}