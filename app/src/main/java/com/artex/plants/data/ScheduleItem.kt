package com.artex.plants.data

enum class Type {
    DAY, ACTION, PLANT
}

abstract class ScheduleItem (open var type: Type)