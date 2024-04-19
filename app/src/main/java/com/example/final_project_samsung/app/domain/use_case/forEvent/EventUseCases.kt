package com.example.final_project_samsung.app.domain.use_case.forEvent

data class EventUseCases(
    val getEvents: GetEvents,
    val deleteEvent: DeleteEvent,
    val getEventById: GetEventById,
    val addEvent: AddEvent
)