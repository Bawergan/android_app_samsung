package com.example.final_project_samsung.app.domain.use_case.forEvent

import com.example.final_project_samsung.app.domain.model.Event
import com.example.final_project_samsung.app.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class GetEvents(
    private val repository: EventRepository
) {
    operator fun invoke(): Flow<List<Event>> {
        return repository.getEvents()
    }
}