package com.artex.plants.data

data class PlantListItem(
    var name: String = "",
    var comment:String = "",
    override var type: Type
    ): ScheduleItem(type)
