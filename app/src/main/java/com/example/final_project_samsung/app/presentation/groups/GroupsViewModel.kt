package com.example.final_project_samsung.app.presentation.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project_samsung.app.domain.model.Event
import com.example.final_project_samsung.app.domain.model.Group
import com.example.final_project_samsung.app.domain.use_case.forEvent.EventUseCases
import com.example.final_project_samsung.app.domain.use_case.forGroup.GroupUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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
        getEvents()
        getGroups()
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

    fun getGroupById(id: Int): Group? {
        var group: Group? = null
        viewModelScope.launch {
            groupUseCases.getGroupById(id)?.also { }
        }
        return group
    }

    fun getEventById(id: Int): Event? {
        var event: Event? = null
        viewModelScope.launch {
            event = eventUseCases.getEventById(id)
        }
        return event
    }

    fun removeEvent(event: Event) {
        viewModelScope.launch {
            eventUseCases.deleteEvent(event)
        }
    }

    fun removeGroup(group: Group) {
        viewModelScope.launch {
            groupUseCases.deleteGroup(group)
        }
    }

    fun addGroup(group: Group) {
        viewModelScope.launch {
            groupUseCases.addGroup(group)
        }
    }

    fun addEvent(event: Event) {
        viewModelScope.launch {
            eventUseCases.addEvent(event)
        }
    }

//    fun getNewGroupData(newName: String): Group {
//        return Group(
//            id = _state.value.groupList.size,
//            groupTags = mutableListOf(newName)
//        )
//    }

//    fun getNewEventData(eventNoNameName: String): Event {
//        return Event(
//            id = _state.value.eventList.size,
//            eventTags = mutableListOf(),
//            startTime = LocalDateTime.now(),
//            endTime = LocalDateTime.now().plusHours(1),
//            groupsForEvent = mutableListOf(0),
//            eventName = eventNoNameName
//        )
//    }

    fun replaceGroup(first: Group, second: Group) {
        removeGroup(first)
        addGroup(second)
    }

    fun replaceEvent(oldEvent: Event, event: Event) {
        removeEvent(oldEvent)
        addEvent(event)
    }
}