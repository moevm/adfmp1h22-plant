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

package com.artex.plants.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.artex.plants.data.Plant
import com.artex.plants.data.Task
import com.example.android.roomwordssample.PlantRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

class PlantViewModel(private val repository: PlantRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allPlants: LiveData<List<Plant>> = repository.allPlants.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun getPlantById(id: Int): LiveData<Plant>  {
        return repository.getPlantById(id).asLiveData()
    }


    fun insert(word: Plant) = viewModelScope.launch {
        repository.insert(word)
    }

    fun update(word: Plant) = viewModelScope.launch {
        repository.update(word)
    }

    val allTasks: LiveData<List<Task>> = repository.allTasks.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun getTaskById(id: Int): LiveData<Task>  {
        return repository.getTaskById(id).asLiveData()
    }


    fun insertTask(task: Task) = viewModelScope.launch {
        repository.insertTask(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        repository.updateTask(task)
    }
}

class PlantViewModelFactory(private val repository: PlantRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlantViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
