package com.example.final_project_samsung.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val tag = "MainViewModel"
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private var eventList = mutableListOf<EventData>()

    private fun getEventWithId(id: Int): EventData {
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
        if ("ended" !in event.tags) {
            event.end()
        }
        updateListOfEventCard()
    }

    fun editEvent(id: Int, newName: String) {
        getEventWithId(id).tags[0] = newName
        updateListOfEventCard()
    }

    fun deleteEvent(id: Int) {
        eventList.remove(getEventWithId(id))
        updateListOfEventCard()
    }

    fun addEventStart() {
        val newEvent = EventData(getNewId())
        newEvent.start()
        eventList.add(newEvent)
        updateListOfEventCard()

        Log.d(tag, "${eventList}, ${_uiState.value.activeEventCard}")
    }

    private var counter = 0
    private fun getNewId(): Int {
        return counter++
    }

    private fun updateListOfEventCard() {
        if (_uiState.value.eventCardList.size != 0) {
            _uiState.value.eventCardList.removeRange(0, _uiState.value.eventCardList.size)
        }
        for (event in eventList) {
            _uiState.value.eventCardList.add(
                CardEventData(
                    event.id,
                    event.startTime,
                    "started",
                    event.tags[0]
                )
            )
            if ("ended" in event.tags) {
                _uiState.value.eventCardList.add(
                    CardEventData(
                        event.id,
                        event.endTime,
                        "ended",
                        event.tags[0]
                    )
                )
            }
        }
        _uiState.value.eventCardList.sortBy { it.time }
    }
}

