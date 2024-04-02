package com.example.final_project_samsung.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.final_project_samsung.data.EventData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val tag = "MainViewModel"
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private var eventList = mutableListOf<EventData>()

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
            event.onEventEnd()
        }
        updateListOfEventCard()
    }

    fun editEventName(id: Int, newName: String) {
        getEventWithId(id).editEventName(newName)
        updateListOfEventCard()
    }

    fun deleteEvent(id: Int) {
        eventList.remove(getEventWithId(id))
        updateListOfEventCard()
    }

    fun addNewEvent() {
        val newEvent = EventData(getNewId())
        newEvent.onEventStart()
        eventList.add(newEvent)
        updateListOfEventCard()

        Log.d(tag, "${eventList}, ${_uiState.value.activeEventCard}")
    }

    fun addTagToEvent(id: Int, newTag: String) {
        getEventWithId(id).addEventToGroup(newTag)
        updateListOfEventCard()
    }

    private var counter = 0
    private fun getNewId(): Int {
        return counter++
    }

    private fun newCard(event: EventData, tag: String): CardEventData {
        return CardEventData(
            event.id,
            event.startTime,
            tag,
            event.eventTags[0],
            event.eventGroups
        )
    }

    private fun updateListOfEventCard() {
        if (_uiState.value.eventCardList.size != 0) {
            _uiState.value.eventCardList.removeRange(0, _uiState.value.eventCardList.size)
        }
        for (event in eventList) {
            _uiState.value.eventCardList.add(newCard(event, "started"))
            if ("ended" in event.eventTags) {
                _uiState.value.eventCardList.add(newCard(event, "ended"))
            }
        }
        _uiState.value.eventCardList.sortBy { it.time }
    }
}

