package com.example.final_project_samsung.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import java.util.Date

class AppUiState {
    var activeEventCard = mutableStateOf<CardEventData?>(null)
    var eventCardList = mutableStateListOf<CardEventData>()
}

data class CardEventData(val eventId: Int, val time: Date, val tag: String, val name: String)