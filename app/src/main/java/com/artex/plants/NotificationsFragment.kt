package com.artex.plants

import android.app.NotificationChannel
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artex.plants.adapters.ChapterAdapter
import com.artex.plants.adapters.NotificationAdapter
import com.artex.plants.adapters.ScheduleAdapter
import com.artex.plants.data.*
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NotificationsFragment : Fragment(R.layout.notifications) {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: NotificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = arrayListOf<Notification>()
        list.add(Notification("Water sunflowe","greenhouse #2", false))
        list.add(Notification("Trim daisy","flowerpot with sun", false))


        recycler = view.findViewById(R.id.notifications_adapter)
        adapter = NotificationAdapter(list)
        recycler.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler.layoutManager = layoutManager

        view.findViewById<Button>(R.id.go_home_btn).setOnClickListener {
            val action = NotificationsFragmentDirections.actionNotificationsFragmentToHomeFragment()
            findNavController().navigate(action)
        }

    }

}