package com.example.final_project_samsung.ui.events

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EventsRoute(
    eventsViewModel: EventsViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit
) {
    val uiState by eventsViewModel.uiState.collectAsStateWithLifecycle()
    EventsScreen(eventsViewModel, uiState, openDrawer)
}