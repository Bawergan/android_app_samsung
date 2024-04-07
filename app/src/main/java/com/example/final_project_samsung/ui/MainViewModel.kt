package com.example.final_project_samsung.ui

import androidx.lifecycle.ViewModel
import com.example.final_project_samsung.data.EventData
import com.example.final_project_samsung.data.GroupData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

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

    fun updateListOfEventCard() {
        if (_uiState.value.eventCardList.size != 0) {
            _uiState.value.eventCardList.removeRange(0, _uiState.value.eventCardList.size)
        }
        for (event in eventList.eventList) {
            _uiState.value.eventCardList.add(newCard(event, "started", event.startTime))
            if ("ended" in event.eventTags) {
                _uiState.value.eventCardList.add(newCard(event, "ended", event.endTime))
            }
        }
        _uiState.value.eventCardList.sortBy { it.time }
    }

    fun addEventToGroup(eventId: Int, groupId: Int) {
        val myEvent = eventList.getEventWithId(eventId)
        val myGroup = groupList.getGroupWithId(groupId)
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

    fun endEvent(id: Int) {
        val event = getEventWithId(id)
        if ("ended" !in event.eventTags) {
//            event.onEventEnd()
        }
    }

    fun editEventName(id: Int, newName: String) {
//        getEventWithId(id).editEventName(newName)
    }

    fun deleteEvent(id: Int) {
        eventList.remove(getEventWithId(id))
    }

    fun addEventToList(newEvent: EventData) {
//        newEvent.onEventStart()
        eventList.add(newEvent)
    }

    fun addTagToEvent(id: Int, newTag: String) {
//        getEventWithId(id).addEventToGroup(newTag)
    }

    private var counter = 0
    fun getNewId(): Int {
        return counter++
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
}