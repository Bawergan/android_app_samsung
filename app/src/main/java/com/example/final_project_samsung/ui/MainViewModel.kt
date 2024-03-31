package com.example.final_project_samsung.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

class MainViewModel : ViewModel() {
    private val tag = "MainViewModel"
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    var eventList = mutableListOf<EventData>()

    fun endEvent(id: Int) {
        lateinit var myEvent: EventData
        for (event in eventList) {
            if (event.id == id) {
                myEvent = event
            }
        }
        if ("ended" !in myEvent.tags) {
            myEvent.endTime = Calendar.getInstance().time
            myEvent.end()
        }
        generateListOfEventCard()
    }

    fun editEvent(id: Int, newName: String) {
        lateinit var myEvent: EventData
        for (event in eventList) {
            if (event.id == id) {
                myEvent = event
            }
        }
        myEvent.tags[0] = newName
        generateListOfEventCard()
    }

    fun deleteEvent(id: Int) {
        lateinit var myEvent: EventData
        for (event in eventList) {
            if (event.id == id) {
                myEvent = event
            }
        }
        eventList.remove(myEvent)
        generateListOfEventCard()
    }

    fun addEventStart() {
        val newEvent = EventData(getNewId())
        newEvent.start()
        eventList.add(newEvent)
        generateListOfEventCard()

        Log.d(tag, "${eventList}, ${_uiState.value.activeEventId}")
    }

    private var counter = 0
    private fun getNewId(): Int {
        return counter++
    }

    private fun generateListOfEventCard() {
        if (_uiState.value.eventCardList.size != 0) {
            _uiState.value.eventCardList.removeRange(0, _uiState.value.eventCardList.size)
        }
        for (event in eventList) {
            _uiState.value.eventCardList.add(CardEventData(event.id, event.startTime, "started", event.tags[0]))
            if ("ended" in event.tags) {
                _uiState.value.eventCardList.add(CardEventData(event.id, event.endTime, "ended", event.tags[0]))
            }
        }
        _uiState.value.eventCardList.sortBy { it.time }
    }
}

