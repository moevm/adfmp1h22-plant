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

package com.artex.plants.interfaces

import androidx.room.*
import com.artex.plants.data.Plant
import kotlinx.coroutines.flow.Flow

/**
 * The Room Magic is in this file, where you map a method call to an SQL query.
 *
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */

@Dao
interface PlantDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM plants ORDER BY name ASC")
    fun getAlphabetizedWords(): Flow<List<Plant>>

    @Query("SELECT * FROM plants WHERE id=:id")
    fun getPlantById(id: Int): Flow<Plant>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Plant)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(word: Plant)

    @Query("DELETE FROM plants")
    suspend fun deleteAll()

    @Query("DELETE FROM tasks WHERE plantId=:id")
    suspend fun deleteTaskByPlantId(id: Int)
}
