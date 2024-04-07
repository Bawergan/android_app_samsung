package com.example.final_project_samsung.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import java.time.LocalDateTime

class AppUiState {
    var activeEventCard = mutableStateOf<CardEventData?>(null)
    var eventCardList = mutableStateListOf<CardEventData>()
    var isInEditMode = mutableStateOf(false )
}

data class CardEventData(
    val eventId: Int,
    val time: LocalDateTime,
    val tag: String,
    val name: String
)