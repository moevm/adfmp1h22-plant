package com.artex.plants.data

data class ScheduleItem (var name:String = "", var comment:String = "", var water:String = "", var feed:String = "", var trim:String = "", val getFromHotbed:String = "", var putInHotbed:String = "")