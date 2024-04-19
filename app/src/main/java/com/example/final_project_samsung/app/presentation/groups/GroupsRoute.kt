package com.example.final_project_samsung.app.presentation.groups

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun GroupsRoute(
    groupsViewModel: GroupsViewModel = hiltViewModel(),
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    navController: NavController,
) {
    val uiState by groupsViewModel.state.collectAsStateWithLifecycle()
    GroupsScreen(groupsViewModel, uiState, openDrawer, navController)
}
