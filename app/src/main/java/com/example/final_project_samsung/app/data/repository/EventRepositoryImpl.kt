package com.example.final_project_samsung.app.data.repository

import com.example.final_project_samsung.app.data.data_source.EventDao
import com.example.final_project_samsung.app.data.data_source.toEntity
import com.example.final_project_samsung.app.data.data_source.toEvent
import com.example.final_project_samsung.app.domain.model.Event
import com.example.final_project_samsung.app.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

class EventRepositoryImpl(
    private val dao: EventDao
) : EventRepository {
    override fun getEvents(): Flow<List<Event>> {
        return dao.getEvents().map { it.map { result -> result.toEvent() } }

//        return flowOf(fakeRepo)
    }

    override suspend fun getEventsById(id: Int): Event? {
        return dao.getEventById(id)?.toEvent()
    }

    override suspend fun insertEvent(event: Event) {
        dao.insertEvent(event.toEntity())
    }

    override suspend fun deleteEvent(event: Event) {
        dao.deleteEvent(event.toEntity())
    }
}

val fakeRepo = listOf(
    Event(
        id = 1,
        eventTags = mutableListOf("my first title"),
        startTime = LocalDateTime.parse("2024-04-07T15:30"),
        endTime = LocalDateTime.parse("2024-04-07T20:30"),
        groupsForEvent = mutableListOf(0)
    ),
    Event(
        id = 2,
        eventTags = mutableListOf("my first title"),
        startTime = LocalDateTime.parse("2024-04-07T15:30"),
        endTime = LocalDateTime.parse("2024-04-07T20:30"),
        groupsForEvent = mutableListOf(0)
    ),
    Event(
        id = 3,
        eventTags = mutableListOf("my first title"),
        startTime = LocalDateTime.parse("2024-04-08T15:30"),
        endTime = LocalDateTime.parse("2024-04-08T20:30"),
        groupsForEvent = mutableListOf(0)
    ),
    Event(
        id = 4,
        eventTags = mutableListOf("my first title"),
        startTime = LocalDateTime.parse("2024-04-07T21:30"),
        endTime = LocalDateTime.parse("2024-04-08T02:30"),
        groupsForEvent = mutableListOf(0)
    ),
)