package com.artex.plants

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.artex.plants.data.Plant
import com.artex.plants.data.ScheduleMode
import com.artex.plants.viewmodels.PlantViewModel
import com.squareup.picasso.Picasso
import java.io.File
import java.net.URI


class PlantFragment : Fragment(R.layout.plant) {

    lateinit var topic: TextView
    lateinit var comment: TextView
    lateinit var date: TextView
    lateinit var plantImage: ImageView
    private val args: PlantFragmentArgs by navArgs()
    lateinit var plant: Plant
    private lateinit var plantViewModel: PlantViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        plant = args.plant

        topic = view.findViewById(R.id.plantTopic)
        comment = view.findViewById(R.id.plantComment)
        date = view.findViewById(R.id.plantDate)
        plantImage = view.findViewById(R.id.plantImage)
        topic.text = plant.name
        comment.text = plant.comment
        date.text = plant.createTime

        val uri = Uri.parse(plant.imagePath)

        Toast.makeText(
            context,
            (context?.let { DocumentFile.fromSingleUri(it, uri) } !== null).toString(),
            Toast.LENGTH_SHORT).show()

        if (plant.imagePath!=""){
            Picasso.get().load(uri).into(plantImage)
        }

        view.findViewById<Button>(R.id.plantSheduleBtn).setOnClickListener {
            val action = PlantFragmentDirections.actionPlantToShedule(mode = ScheduleMode.LOCAL)
            findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.carePlanBtn).setOnClickListener {
            val action = PlantFragmentDirections.actionPlantToCarePlan(plant)
            findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.notificationsBtn).setOnClickListener {
            val action = PlantFragmentDirections.actionPlantToNotificationsFragment()
            findNavController().navigate(action)
        }

        plantImage.setOnClickListener {
            openActivityForResult()
        }

        val activity: MainActivity = activity as MainActivity
        plantViewModel = activity.plantViewModel
        plantViewModel.allPlants.observe(activity) { plants ->
            plants.let {
                for (item in it){
                    if (item.id == args.plant.id){
                        plant = item
                    }
                }
            }
        }
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent = result.data!!
            val currentUri: Uri? = data!!.data
            Picasso.get().load(currentUri).into(plantImage)
            plant.imagePath = currentUri!!.toString()
            plantViewModel.update(plant)
        }
    }

    fun openActivityForResult() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_OPEN_DOCUMENT
        resultLauncher.launch(intent)
    }

}