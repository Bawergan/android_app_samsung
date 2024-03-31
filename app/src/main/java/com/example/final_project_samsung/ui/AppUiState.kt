package com.example.final_project_samsung.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class AppUiState {
    var eventList = mutableStateListOf<EventData>()
    var activeEventId = mutableStateOf<Int?>(null)
}
