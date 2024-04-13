package com.example.final_project_samsung.ui.groups

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.final_project_samsung.data.EventData
import com.example.final_project_samsung.data.GroupData
import java.time.LocalDateTime

class GroupsUiState {
    var eventCardList = mutableStateListOf<CardEventData>()
    var groupList = mutableStateListOf<GroupData>()
    var eventList = mutableStateListOf<EventData>()

    var isInEditMode = mutableStateOf(false)
    var isMainActionButtonClicked = mutableStateOf(false)
    var isGroupEditingBottomSheetOpen = mutableStateOf(false)
    var isEventEditingBottomSheetOpen = mutableStateOf(false)

    var chosenGroup = mutableStateOf<Int?>(null)
    var chosenEvent = mutableStateOf<Int?>(null)
}


data class CardEventData(
    val eventId: Int,
    val time: LocalDateTime,
    val tag: String,
    val name: String
)