/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.roomwordssample

import androidx.annotation.WorkerThread
import com.artex.plants.data.Plant
import com.artex.plants.data.Task
import com.artex.plants.interfaces.TaskDao
import com.artex.plants.interfaces.PlantDao
import kotlinx.coroutines.flow.Flow

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class PlantRepository(private val plantDao: PlantDao, private val taskDao: TaskDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allPlants: Flow<List<Plant>> = plantDao.getAlphabetizedWords()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.

    fun getPlantById(id: Int): Flow<Plant> {
        return plantDao.getPlantById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(plant: Plant) {
        plantDao.insert(plant)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(plant: Plant) {
        plantDao.update(plant)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deletePlantById(id: Int) {
        plantDao.deletePlantById(id)
    }

    val allTasks: Flow<List<Task>> = taskDao.getAlphabetizedWords()

    fun getTaskById(id: Int): Flow<Task> {
        return taskDao.getPlantById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertTask(task: Task) {
        taskDao.insert(task)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateTask(task: Task) {
        taskDao.update(task)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllTasks() {
        taskDao.deleteAll()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteTasksByPlantId(id: Int) {
        taskDao.deleteTasksByPlantId(id)
    }
}
