package com.example.final_project_samsung.app.presentation.addEditScreen.forEvent

import java.time.LocalDateTime

sealed class AddEditEventEvent {
    data object Save : AddEditEventEvent()
    data object Delete : AddEditEventEvent()
    data class ChangeName(val value: String) : AddEditEventEvent()
    data class ChangeStartTime(val value: LocalDateTime) : AddEditEventEvent()
    data class ChangeEndTime(val value: LocalDateTime) : AddEditEventEvent()
    data class ChangeGroup(val value: Int) : AddEditEventEvent()
}
