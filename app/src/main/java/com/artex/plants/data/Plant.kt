package com.artex.plants.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "name") var name: String = "",
    var water: String = "",
    var trim: String = "",
    var feed: String = "",
    var getFromHotbed: String = "",
    var putInHotbed: String = "",
    var comment:String = "Your comment"
)
