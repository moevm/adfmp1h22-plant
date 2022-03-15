package com.artex.plants.data
import androidx.room.Entity

@Entity(tableName = "plants")
data class Plant(
    var name: String = "",
    var water: String = "",
    var trim: String = "",
    var feed: String = "",
    var getFromHotbed: String = "",
    var putInHotbed: String = ""
)
