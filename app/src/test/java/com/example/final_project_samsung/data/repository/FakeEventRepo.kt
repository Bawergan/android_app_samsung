package com.example.final_project_samsung.data.repository

import com.example.final_project_samsung.app.domain.model.Event
import com.example.final_project_samsung.app.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeEventRepo : EventRepository {
    private val events = mutableListOf<Event>()
    override fun getEvents(): Flow<List<Event>> {
        return flow { emit(events) }
    }

    override suspend fun getEventsById(id: Int): Event? {
        return events.find { it.eventId == id }
    }

    override suspend fun insertEvent(event: Event) {
        events.add(event)
    }

    override suspend fun deleteEvent(event: Event) {
        events.remove(event)
    }
}