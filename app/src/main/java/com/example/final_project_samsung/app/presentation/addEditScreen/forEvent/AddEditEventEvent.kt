package com.example.final_project_samsung.app.presentation.addEditScreen.forEvent

import java.time.LocalDateTime

sealed class AddEditEventEvent {
    data object SaveEvent : AddEditEventEvent()
    data object DeleteEvent : AddEditEventEvent()
    data class ChangeEventName(val value: String) : AddEditEventEvent()
    data class ChangeStartTime(val value: LocalDateTime) : AddEditEventEvent()
    data class ChangeEndTime(val value: LocalDateTime) : AddEditEventEvent()
}
