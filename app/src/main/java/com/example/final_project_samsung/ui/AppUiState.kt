package com.example.final_project_samsung.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import java.util.Date

class AppUiState {
    var eventList = mutableStateListOf<EventData>()
    var activeEventId = mutableStateOf<Int?>(null)
    var eventCardList = mutableStateListOf<CardEventData>()
}

class CardEventData(val id: Int, val time: Date, val tag: String)