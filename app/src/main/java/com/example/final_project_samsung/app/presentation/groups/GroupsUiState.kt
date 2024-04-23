package com.example.final_project_samsung.app.presentation.groups

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.final_project_samsung.app.domain.model.Event
import com.example.final_project_samsung.app.domain.model.Group
import com.example.final_project_samsung.app.presentation.groups.customLazyLayout.DISPLAY_DENSITY
import com.example.final_project_samsung.app.presentation.groups.customLazyLayout.ListItem
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.math.roundToInt

data class GroupsUiState(
    val groupList: List<Group> = emptyList(),
    val eventList: List<Event> = emptyList(),

    var isMainActionButtonClicked: MutableState<Boolean> = mutableStateOf(false),
    var isGroupEditingBottomSheetOpen: MutableState<Boolean> = mutableStateOf(false),
    var isEventEditingBottomSheetOpen: MutableState<Boolean> = mutableStateOf(false),

    var chosenGroup: MutableState<Int?> = mutableStateOf(null),
    var chosenEvent: MutableState<Int?> = mutableStateOf(null),

    val pdForGroup: Int = (600 / DISPLAY_DENSITY).roundToInt(),
    val pdForHour: Int = (300 / DISPLAY_DENSITY).roundToInt(),
    val items: List<ListItem> = emptyList(),

    val _currentTime: MutableState<LocalDateTime> = mutableStateOf(LocalDateTime.now()),
    val offsetForCurrentTime: Int = ((_currentTime.value.toEpochSecond(ZoneOffset.UTC) / (60f * 60f)) * pdForHour).toInt()
)