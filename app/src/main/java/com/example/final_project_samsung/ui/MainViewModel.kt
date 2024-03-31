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

    fun endEvent(id: Int) {
        lateinit var myEvent: EventData
        for (event in _uiState.value.eventList) {
            if (event.id == id) {
                myEvent = event
            }
        }
        myEvent.endTime = Calendar.getInstance().time
        myEvent.end()
    }

    fun editEvent(id: Int) {

    }

    fun deleteEvent(id: Int) {
        lateinit var myEvent: EventData
        for (event in _uiState.value.eventList) {
            if (event.id == id) {
                myEvent = event
            }
        }
        _uiState.value.eventList.remove(myEvent)
    }

    fun addEventStart() {
        val newEvent = EventData(getNewId())
        newEvent.start()
        _uiState.value.eventList.add(newEvent)

        Log.d(tag, "${_uiState.value.eventList}, ${_uiState.value.activeEventId}")

    }

    private var counter = 0
    private fun getNewId(): Int {
        return counter++
    }

}