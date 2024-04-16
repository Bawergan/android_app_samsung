package com.example.final_project_samsung.app.presentation.weeks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun WeeksRoute(weeksViewModel: WeeksViewModel, isExpandedScreen: Boolean, openDrawer: () -> Unit) {
    val uiState by weeksViewModel.uiState.collectAsStateWithLifecycle()
    WeeksScreen(weeksViewModel, uiState, openDrawer)
}