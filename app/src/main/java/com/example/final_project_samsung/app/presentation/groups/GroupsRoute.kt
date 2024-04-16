package com.example.final_project_samsung.app.presentation.groups

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GroupsRoute(
    groupsViewModel: GroupsViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit
) {
    val uiState by groupsViewModel.uiState.collectAsStateWithLifecycle()
    GroupsScreen(groupsViewModel, uiState, openDrawer)
}
