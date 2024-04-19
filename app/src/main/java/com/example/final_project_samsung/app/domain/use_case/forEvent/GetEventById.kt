package com.example.final_project_samsung.app.domain.use_case.forEvent

import com.example.final_project_samsung.app.domain.model.Event
import com.example.final_project_samsung.app.domain.repository.EventRepository

class GetEventById(
    private val repository: EventRepository
) {
    suspend operator fun invoke(id: Int): Event? {
        return repository.getEventsById(id)
    }
}