package com.example.final_project_samsung.app.presentation.groups

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime

class GroupsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GroupsUiState())
    val uiState: StateFlow<GroupsUiState> = _uiState.asStateFlow()

    fun getNewGroupData(newName: String): com.example.final_project_samsung.app.data.GroupData {
        return com.example.final_project_samsung.app.data.GroupData(
            _uiState.value.groupList.size + 1,
            groupTags = mutableListOf(newName)
        )
    }

    fun getNewEventData(eventNoNameName: String): com.example.final_project_samsung.app.data.EventData {
        return com.example.final_project_samsung.app.data.EventData(
            _uiState.value.eventList.size + 1,
            mutableListOf(eventNoNameName),
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(1),
            mutableListOf(com.example.final_project_samsung.app.data.GroupData(0))
        )

    }
}