package com.example.final_project_samsung.app.presentation.groups

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.final_project_samsung.app.domain.model.Event
import com.example.final_project_samsung.app.domain.model.Group

data class GroupsUiState(
    val groupList: List<Group> = emptyList(),
    val eventList: List<Event> = emptyList(),

    var isMainActionButtonClicked: MutableState<Boolean> = mutableStateOf(false),
    var isGroupEditingBottomSheetOpen: MutableState<Boolean> = mutableStateOf(false),
    var isEventEditingBottomSheetOpen: MutableState<Boolean> = mutableStateOf(false),

    var chosenGroup: MutableState<Int?> = mutableStateOf(null),
    var chosenEvent: MutableState<Int?> = mutableStateOf(null)
)