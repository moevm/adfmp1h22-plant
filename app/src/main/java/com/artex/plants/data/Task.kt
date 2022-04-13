package com.artex.plants.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "name") var name: String = "",
    var plantId: Int = 0,
    var action: String = "",
    var comment: String = "Your comment",
    var taskDate: String = getCurrentDate(),
    var isDone: Boolean = false,
) : Parcelable