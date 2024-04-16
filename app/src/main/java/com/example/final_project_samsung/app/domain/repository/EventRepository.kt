package com.example.final_project_samsung.app.domain.repository

import com.example.final_project_samsung.app.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEvents(): Flow<List<Event>>
    suspend fun getEventsById(id: Int): Event?
    suspend fun insertEvent(event: Event)
    suspend fun deleteEvent(event: Event)
}