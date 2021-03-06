package com.artex.plants

import com.artex.plants.data.Plant
import com.artex.plants.data.PlantListItem
import com.artex.plants.data.Type
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.Comparator

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ScheduleTests {

    val modes = arrayOf<String>(
        "every 7 days",
        "every 6 days",
        "every 5 days",
        "every 4 days",
        "every 3 days",
        "every 2 days",
        "every 1 days",
        "never"
    )

    fun getPosition(mode: String, modes: Array<String>): Int {
        for (i in 0..modes.size - 1) {
            if (mode == modes[i]) {
                return i + 1
            }
        }
        return 1
    }

    @Test
    fun getPositionTest() {
        assertEquals(1, getPosition("every 7 days", modes))
        assertEquals(2, getPosition("every 6 days", modes))
        assertEquals(3, getPosition("every 5 days", modes))
        assertEquals(4, getPosition("every 4 days", modes))
        assertEquals(5, getPosition("every 3 days", modes))
        assertEquals(6, getPosition("every 2 days", modes))
        assertEquals(7, getPosition("every 1 days", modes))
        assertEquals(8, getPosition("never", modes))
    }

    fun getPlantListItem(
        plant: Plant,
        i: Int,
        dateBefore: LocalDate,
        dateAfter: LocalDate,
        formatter: DateTimeFormatter
    ): PlantListItem {
        val daysBetween: Long = ChronoUnit.DAYS.between(dateAfter, dateBefore)

        val waterPosition = modes.size - getPosition(plant.water, modes)
        val water = waterPosition != 0 && ((daysBetween).toInt() % waterPosition) == 0

        val feedPosition = modes.size - getPosition(plant.feed, modes)
        val feed = feedPosition != 0 && ((daysBetween).toInt() % feedPosition) == 0

        val trimPosition = modes.size - getPosition(plant.trim, modes)
        val trim = trimPosition != 0 && ((daysBetween).toInt() % trimPosition) == 0

        var date = dateBefore.format(formatter)

        if (i == 0) date = "Today " + date
        if (i == 1) date = "Tomorrow " + date

        return PlantListItem(
            plant.id,
            plant.name,
            plant.comment,
            water = water,
            feed = feed,
            trim = trim,
            getFromHotbed = plant.getFromHotbed,
            putInHotbed = plant.putInHotbed,
            isHotbedUse = plant.isHotbedUse,
            date = date,
            type = Type.PLANT
        )
    }

    @Test
    fun createScheduleTests() {

        val plant = Plant(
            name = "Dairy",
            createTime = "14.04.2022",
            water = "every 1 days",
            trim = "every 2 days",
            feed = "never"
        )

        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        var dateBefore: LocalDate = LocalDate.parse("14.04.2022", formatter)
        val dateAfter: LocalDate = LocalDate.parse(plant.createTime, formatter)

        val plantListItemToday = getPlantListItem(plant,0,dateBefore,dateAfter,formatter)
        assertEquals(true, plantListItemToday.date.startsWith("Today"))
        assertEquals(true, plantListItemToday.water)
        assertEquals(true, plantListItemToday.trim)
        assertEquals(false, plantListItemToday.feed)

        dateBefore = dateBefore.plusDays(1)
        val plantListItemTomorrow = getPlantListItem(plant,1,dateBefore,dateAfter,formatter)
        assertEquals(true, plantListItemTomorrow.date.startsWith("Tomorrow"))
        assertEquals(true, plantListItemTomorrow.water)
        assertEquals(false, plantListItemTomorrow.trim)
        assertEquals(false, plantListItemTomorrow.feed)

        dateBefore = dateBefore.plusDays(1)
        val plantListItemAfterTomorrow = getPlantListItem(plant,2,dateBefore,dateAfter,formatter)
        assertEquals(false, plantListItemAfterTomorrow.date.startsWith("Today"))
        assertEquals(false, plantListItemAfterTomorrow.date.startsWith("Tomorrow"))
        assertEquals(true, plantListItemAfterTomorrow.water)
        assertEquals(true, plantListItemAfterTomorrow.trim)
        assertEquals(false, plantListItemAfterTomorrow.feed)
    }

    @Test
    fun sortPlantsTests() {
        val list = arrayListOf<Plant>()
        list.add(Plant(id=1,createTime = "14.12.2021"))
        list.add(Plant(id=2,createTime = "17.12.2013"))
        list.add(Plant(id=3,createTime = "04.01.2022"))
        list.add(Plant(id=4,createTime = "11.12.2021"))
        list.add(Plant(id=5,createTime = "11.12.2019"))
        list.add(Plant(id=6,createTime = "10.12.2021"))

        list.sortWith(ComparePlantByDates)
        assertEquals(2,list[0].id)
        assertEquals(5,list[1].id)
        assertEquals(6,list[2].id)
        assertEquals(4,list[3].id)
        assertEquals(1,list[4].id)
        assertEquals(3,list[5].id)
    }

    class ComparePlantByDates {
        companion object : Comparator<Plant> {
            override fun compare(a: Plant, b: Plant): Int {

                val formatter = SimpleDateFormat("dd.MM.yyyy")
                val dateA: Date = formatter.parse(a.createTime)
                val dateB: Date = formatter.parse(b.createTime)
                if (dateA.after(dateB)) {
                    return 1
                }
                if (dateB.after(dateA)) {
                    return -1
                }
                return 0
            }
        }
    }



}