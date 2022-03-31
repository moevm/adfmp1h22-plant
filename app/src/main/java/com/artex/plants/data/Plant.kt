package com.artex.plants.data
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "name") var name: String = "",
    var water: String = "",
    var trim: String = "",
    var feed: String = "",
    var getFromHotbed: String = "",
    var putInHotbed: String = "",
    var comment:String = "Your comment",
    var createTime: String = getCurrentDate(),
    var imagePath:String = ""
): Parcelable

fun getCurrentDate():String{
    val sdf = SimpleDateFormat("dd.MM.yyyy")
    return sdf.format(Date())
}