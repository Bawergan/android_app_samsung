package com.example.final_project_samsung.app.presentation.groups

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val eventUseCases: EventUseCases,
    private val groupUseCases: GroupUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(GroupsUiState())
    val state: StateFlow<GroupsUiState> = _state.asStateFlow()

    private val _stateLayout = MutableStateFlow(State())
    val stateLayout = _stateLayout

    private var getEventsJob: Job? = null
    private var getGroupsJob: Job? = null
    private var inflateJob: Job? = null

    private var _currentTime = mutableStateOf(LocalDateTime.now())

    init {
        getEvents()
        getGroups()
        inflateLayout()
    }

    private fun inflateLayout() {
        inflateJob = viewModelScope.launch {
            val items = mutableListOf<ListItem>()
            //Add Events
            items.addAll((0 until _state.value.eventList.size).map {
                val item = _state.value.eventList[it]
                val y =
                    (item.startTime.toEpochSecond(ZoneOffset.UTC) / (60f * 60f)) * _stateLayout.value.pdForHour.value
                ListItem(
                    x = item.groupId.times(100),
                    y = y.toInt(),
                    dataArray = item.toMap(),
                    itemType = ItemType.Event
                )
            })
            //Add Line
            items.add(
                ListItem(
                    y = ((_currentTime.value.toEpochSecond(ZoneOffset.UTC) / (60f * 60f)) * _stateLayout.value.pdForHour.value).toInt(),
                    x = 0,
                    dataArray = null,
                    itemType = ItemType.Line
                )
            )

            _stateLayout.value = _stateLayout.value.copy(
                items = items.toList()
            )
        }
    }


    private fun getEvents() {
        getEventsJob?.cancel()
        getEventsJob = eventUseCases.getEvents()
            .onEach { events ->
                _state.value = state.value.copy(
                    eventList = events
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getGroups() {
        getGroupsJob?.cancel()
        getGroupsJob = groupUseCases.getGroups()
            .onEach { groups ->
                _state.value = state.value.copy(
                    groupList = groups
                )
            }
            .launchIn(viewModelScope)
    }
}

data class State(
    val pdForHour: Dp = 100.dp,
    val items: List<ListItem> = emptyList()
)