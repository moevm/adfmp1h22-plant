package com.artex.plants.data

data class Message(
    var message: String = "",
    override var type: Type
    ): ScheduleItem(type)
