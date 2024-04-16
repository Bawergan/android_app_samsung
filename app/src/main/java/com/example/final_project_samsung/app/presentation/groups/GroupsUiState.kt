package com.example.final_project_samsung.app.presentation.groups

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class GroupsUiState {
    var groupList = mutableStateListOf<com.example.final_project_samsung.app.data.GroupData>()
    var eventList = mutableStateListOf<com.example.final_project_samsung.app.data.EventData>()

    var isMainActionButtonClicked = mutableStateOf(false)
    var isGroupEditingBottomSheetOpen = mutableStateOf(false)
    var isEventEditingBottomSheetOpen = mutableStateOf(false)

    var chosenGroup = mutableStateOf<Int?>(null)
    var chosenEvent = mutableStateOf<Int?>(null)
}