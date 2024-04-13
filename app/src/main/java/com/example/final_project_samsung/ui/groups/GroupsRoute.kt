package com.example.final_project_samsung.ui.groups

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GroupsRoute(
    GroupsViewModel: GroupsViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit
) {
    val uiState by GroupsViewModel.uiState.collectAsStateWithLifecycle()
    GroupsScreen(GroupsViewModel, uiState, openDrawer)
}
