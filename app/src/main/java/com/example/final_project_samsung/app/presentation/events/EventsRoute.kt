package com.example.final_project_samsung.app.presentation.events

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EventsRoute(
    eventsViewModel: EventsViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit
) {
    val state by eventsViewModel.state.collectAsStateWithLifecycle()
    EventsScreen(eventsViewModel, state, openDrawer)
}