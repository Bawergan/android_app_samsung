package com.example.final_project_samsung.app.data.data_source.eventData

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM event")
    fun getEvents(): Flow<List<EntityEvent>>

    @Query("SELECT * FROM event WHERE id = :id")
    suspend fun getEventById(id: Int): EntityEvent?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EntityEvent)

    @Delete
    suspend fun deleteEvent(event: EntityEvent)
}