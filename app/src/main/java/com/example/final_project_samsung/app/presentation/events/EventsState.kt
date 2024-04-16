package com.example.final_project_samsung.app.presentation.events

import com.example.final_project_samsung.app.domain.model.Event

data class EventsState(
    val eventList: List<Event> = emptyList()
)
