package com.example.final_project_samsung.app.presentation.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project_samsung.app.domain.use_case.forEvent.EventUseCases
import com.example.final_project_samsung.app.domain.use_case.forGroup.GroupUseCases
import com.example.final_project_samsung.app.presentation.groups.customLazyLayout.ItemType
import com.example.final_project_samsung.app.presentation.groups.customLazyLayout.ListItem
import com.example.final_project_samsung.app.presentation.groups.customLazyLayout.toMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val eventUseCases: EventUseCases,
    private val groupUseCases: GroupUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(GroupsUiState())
    val state: StateFlow<GroupsUiState> = _state.asStateFlow()

    private var getEventsJob: Job? = null
    private var getGroupsJob: Job? = null

    init {
        lunchJobs()
    }

    private fun lunchJobs() {
        getEventsJob?.cancel()
        getGroupsJob?.cancel()

        getEventsJob = eventUseCases.getEvents()
            .onEach { events ->
                val items = events.map { item ->
                    val fromY =
                        (item.startTime.toEpochSecond(ZoneOffset.UTC) / (60f * 60f)) * _state.value.pdForHour
                    val toY =
                        (item.endTime.toEpochSecond(ZoneOffset.UTC) / (60f * 60f)) * _state.value.pdForHour

                    ListItem(
                        fromX = item.groupId * (_state.value.pdForGroup),
                        fromY = fromY.toInt() - _state.value.offsetForCurrentTime,
                        toX = ((item.groupId + 1) * (_state.value.pdForGroup)),
                        toY = toY.toInt() - _state.value.offsetForCurrentTime,
                        dataArray = item.toMap(),
                        itemType = ItemType.Event
                    )
                }.toMutableList()
                _state.value = state.value.copy(
                    eventList = events,
                    items = items.toList()
                )
            }
            .launchIn(viewModelScope)

        getGroupsJob = groupUseCases.getGroups()
            .onEach { groups ->
                _state.value = state.value.copy(
                    groupList = groups,
                )
            }
            .launchIn(viewModelScope)
    }
}