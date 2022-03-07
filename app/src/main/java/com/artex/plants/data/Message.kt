package com.artex.plants.data

data class Message(
    var text: String = "",
    override var type: Type
    ): ScheduleItem(type)
