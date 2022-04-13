package com.artex.plants

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.artex.plants.broadcast.MemoBroadcast
import com.artex.plants.room.PlantApplication
import com.artex.plants.viewmodels.PlantViewModel
import com.artex.plants.viewmodels.PlantViewModelFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    val plantViewModel: PlantViewModel by viewModels {
        PlantViewModelFactory((application as PlantApplication).plantRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bundle: Bundle? = intent.extras

        bundle?.let {
            val string: String? = it.getString("key")
            if (string.equals("notify")) {
                Toast.makeText(applicationContext, "NOTIFY", Toast.LENGTH_SHORT).show()

                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                val inflater = navHostFragment.navController.navInflater
                val graph = inflater.inflate(R.navigation.nav_graph)

                graph.setStartDestination(R.id.notificationsFragment)

                val navController = navHostFragment.navController
                navController.setGraph(graph, intent.extras)
            }
        }



        NotificationChannel()

        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 9
        calendar[Calendar.MINUTE] = 34
        calendar[Calendar.SECOND] = 40

        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val intent = Intent(this@MainActivity, MemoBroadcast::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }

    private fun NotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "PASTICCINO"
            val description = "PASTICCINO`S CHANNEL"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Notification", name, importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

}