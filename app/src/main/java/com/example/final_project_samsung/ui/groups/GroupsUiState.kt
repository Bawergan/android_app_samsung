package com.example.final_project_samsung.ui.groups

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.final_project_samsung.data.EventData
import com.example.final_project_samsung.data.GroupData

class GroupsUiState {
    var groupList = mutableStateListOf<GroupData>()
    var eventList = mutableStateListOf<EventData>()

    var isMainActionButtonClicked = mutableStateOf(false)
    var isGroupEditingBottomSheetOpen = mutableStateOf(false)
    var isEventEditingBottomSheetOpen = mutableStateOf(false)

    var chosenGroup = mutableStateOf<Int?>(null)
    var chosenEvent = mutableStateOf<Int?>(null)
}