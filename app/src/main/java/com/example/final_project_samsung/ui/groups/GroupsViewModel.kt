package com.example.final_project_samsung.ui.groups

import androidx.lifecycle.ViewModel
import com.example.final_project_samsung.data.EventData
import com.example.final_project_samsung.data.GroupData
import com.example.final_project_samsung.data.listOfEventData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime

class GroupsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GroupsUiState())
    val uiState: StateFlow<GroupsUiState> = _uiState.asStateFlow()

    var groupList = GroupList()
    var eventList = EventList()

    private fun newCard(event: EventData, tag: String, time: LocalDateTime): CardEventData {
        return CardEventData(
            event.id,
            time,
            tag,
            event.eventTags[0]
        )
    }

    fun updateLazyLayout() {
//        if (_uiState.value.eventCardList.size != 0) {
//            _uiState.value.eventCardList.removeRange(0, _uiState.value.eventCardList.size)
//        }
//        for (event in eventList.eventList) {
//            _uiState.value.eventCardList.add(newCard(event, "started", event.startTime))
//            if ("ended" in event.eventTags) {
//                _uiState.value.eventCardList.add(newCard(event, "ended", event.endTime))
//            }
//        }
//        _uiState.value.eventCardList.sortBy { it.time }
        _uiState.value.groupList.add(GroupData(999999))
        _uiState.value.groupList.removeLast()

        _uiState.value.eventList.add(EventData(999999))
        _uiState.value.eventList.removeLast()

    }

    fun passeToListOfEventData(eventData: EventData) {
        listOfEventData.add(eventData)
    }
}

class EventList {
    var eventList = mutableListOf<EventData>()

    fun getEventWithId(id: Int): EventData {
        lateinit var myEvent: EventData
        for (event in eventList) {
            if (event.id == id) {
                myEvent = event
            }
        }
        return myEvent
    }

    private var counter = 0
    fun getNewId(): Int {
        return counter++
    }

    fun getNewEventData(eventNoNameName: String): EventData {
        return EventData(
            getNewId(),
            mutableListOf(eventNoNameName),
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(1),
            mutableListOf(GroupData(0))
        )
    }
}

class GroupList {
    val groupList = mutableListOf<GroupData>()

    fun addNewGroup() {
        groupList.add(GroupData(getNewId()))
    }

    private var counter = 0
    private fun getNewId(): Int {
        return counter++
    }

    fun getGroupWithId(id: Int): GroupData {
        lateinit var myGroup: GroupData
        for (group in groupList) {
            if (group.id == id) {
                myGroup = group
            }
        }
        return myGroup
    }

    fun getNewGroupData(newName: String): GroupData {
        return GroupData(getNewId(), groupTags = mutableListOf(newName))
    }
}