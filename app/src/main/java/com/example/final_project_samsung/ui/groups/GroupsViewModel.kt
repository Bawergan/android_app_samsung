package com.example.final_project_samsung.ui.groups

import androidx.lifecycle.ViewModel
import com.example.final_project_samsung.data.EventData
import com.example.final_project_samsung.data.GroupData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime

class GroupsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GroupsUiState())
    val uiState: StateFlow<GroupsUiState> = _uiState.asStateFlow()

    fun getNewGroupData(newName: String): GroupData {
        return GroupData(_uiState.value.groupList.size + 1, groupTags = mutableListOf(newName))
    }

    fun getNewEventData(eventNoNameName: String): EventData {
        return EventData(
            _uiState.value.eventList.size + 1,
            mutableListOf(eventNoNameName),
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(1),
            mutableListOf(GroupData(0))
        )

    }
}