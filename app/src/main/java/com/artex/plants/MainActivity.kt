package com.artex.plants

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.artex.plants.broadcast.NotificationBroadcast
import com.artex.plants.room.PlantApplication
import com.artex.plants.viewmodels.PlantViewModel
import com.artex.plants.viewmodels.PlantViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


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



        notificationChannel("Notification")

        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 9
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0

        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val intent = Intent(this@MainActivity, NotificationBroadcast::class.java)
        intent.putExtra("key",201)
        intent.putExtra("channelId","Notification")

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

    private fun notificationChannel(id: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Plant notifications"
            val description = "Plant notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(id, name, importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun countOutstandingTasks(){
        val oldTasks = plantViewModel.allTasks.value
        var countNotDoneTasks = 0
        if (oldTasks != null){
            for (task in oldTasks){
                if (!task.isDone) countNotDoneTasks++
            }
        }

        if (countNotDoneTasks > 0) {
            val mSettings: SharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
            if(mSettings.contains("last_notification_time")) {
                val lastNotificationTime = mSettings.getString("last_notification_time", "")
                val formatter = SimpleDateFormat("yyyyMMdd_HHmmss")
                val date: Date = formatter.parse(lastNotificationTime)
                val dateNow = Date()

                val diffInMillies: Long = Math.abs(dateNow.getTime() - date.getTime())
                val diff: Long = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS)

                if (diff > 2 && dateNow.hours > 9 && dateNow.hours < 21) {
                    val editor: SharedPreferences.Editor = mSettings.edit()
                    val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                    val currentDateAndTime: String = sdf.format(Date())
                    editor.putString("last_notification_time", currentDateAndTime)
                    editor.apply()

                    notificationChannel("channelId")

                    val intent = Intent(applicationContext, NotificationBroadcast::class.java)
                    intent.putExtra("key",201)
                    intent.putExtra("channelId","channelId")

                    val pendingIntent = PendingIntent.getBroadcast(
                        applicationContext,
                        2,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    )

                    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val cal = Calendar.getInstance()
                    cal.time = Date()
                    cal.add(Calendar.HOUR, 2)

                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        cal.timeInMillis,
                        pendingIntent
                    )

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //countOutstandingTasks()
    }

}