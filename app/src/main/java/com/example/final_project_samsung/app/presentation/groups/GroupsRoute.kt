package com.example.final_project_samsung.app.presentation.groups

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.final_project_samsung.R
import com.example.final_project_samsung.app.presentation.appNavigation.TheAppDestinations
import com.example.final_project_samsung.app.presentation.groups.customLazyLayout.CustomLazyLayoutScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun GroupsRoute(
    groupsViewModel: GroupsViewModel = hiltViewModel(),
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    navController: NavController,
) {
    val groupsUiState by groupsViewModel.state.collectAsStateWithLifecycle()
    val topAppBarState = rememberTopAppBarState()
    Scaffold(
        topBar = { GroupsTopAppBar(openDrawer = openDrawer, topAppBarState = topAppBarState) },
        floatingActionButton = {
            FloatingActionButtonManager(groupsUiState, navController)
        }
    ) {
        Box(Modifier.padding(it)) {
            CustomLazyLayoutScreen(groupsUiState, navController)
        }
    }
//    GroupsScreen(groupsViewModel, uiState, openDrawer, navController)

//    LazyColumn {
//        stickyHeader { Text(text = "sd") }
//    }
}


@Composable
fun FloatingActionButtonManager(groupsUiState: GroupsUiState, navController: NavController) {
    if (groupsUiState.isMainActionButtonClicked.value) {
        Column {
            Button(onClick = {
                navController.navigate(
                    TheAppDestinations.ADD_EDIT_GROUP_ROUTE +
                            "?groupId=${-1}"
                )
            }) {
                Text(text = "New Group")
            }
            Button(onClick = {
                navController.navigate(
                    TheAppDestinations.ADD_EDIT_EVENT_ROUTE +
                            "?eventId=${-1}"
                )
            }) {
                Text(text = "New Event")
            }
        }
    } else {
        FloatingActionButton(onClick = {
            groupsUiState.isMainActionButtonClicked.value = true
        }) {
            Icon(
                Icons.Filled.Add, "add time stamp"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GroupsTopAppBar(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    topAppBarState: TopAppBarState = rememberTopAppBarState(),
    scrollBehavior: TopAppBarScrollBehavior? =
        TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.group_view_title))
        },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(R.string.cd_open_navigation_drawer),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}
