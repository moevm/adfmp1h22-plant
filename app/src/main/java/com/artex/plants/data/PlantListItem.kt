package com.artex.plants.data

data class PlantListItem(
    var name: String = "",
    var comment: String = "",
    var water: Boolean = false,
    var trim: Boolean = false,
    var feed: Boolean = false,
    var getFromHotbed: String = "",
    var putInHotbed: String = "",
    var isHotbedUse: Boolean = true,
    var date: String = "",
    override var type: Type
) : ScheduleItem(type)
