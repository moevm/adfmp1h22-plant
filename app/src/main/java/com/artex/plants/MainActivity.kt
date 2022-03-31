package com.artex.plants

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.artex.plants.room.PlantApplication
import com.artex.plants.viewmodels.PlantViewModel
import com.artex.plants.viewmodels.WordViewModelFactory

class MainActivity : AppCompatActivity() {

    val plantViewModel: PlantViewModel by viewModels {
        WordViewModelFactory((application as PlantApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        val navController = navHostFragment.navController
        navController.setGraph(graph, intent.extras)
    }
}