package com.example.final_project_samsung.ui.events

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.final_project_samsung.R
import com.example.final_project_samsung.data.EventData
import com.example.final_project_samsung.data.listOfEventData
import com.example.final_project_samsung.utils.timeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(
    eventsViewModel: EventsViewModel,
    eventsUiState: EventsUiState,
    openDrawer: () -> Unit
) {
    eventsUiState.eventList = listOfEventData.toMutableStateList()
    val topAppBarState = rememberTopAppBarState()
    Scaffold(
        topBar = { EventsTopAppBar(openDrawer = openDrawer, topAppBarState = topAppBarState) },
    ) {
        Box(modifier = Modifier.padding(it)) {
            LazyColumn {
                items(eventsUiState.eventList) { eventData ->
                    MakeCard(eventData)
                }
            }
        }
    }
}

@Composable
fun MakeCard(eventData: EventData) {
    Card {
        Text(text = eventData.eventTags[0])
        Text(
            text = "${eventData.startTime.format(timeFormatter)} - ${
                eventData.endTime.format(
                    timeFormatter
                )
            }"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventsTopAppBar(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    topAppBarState: TopAppBarState = rememberTopAppBarState(),
    scrollBehavior: TopAppBarScrollBehavior? =
        TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.event_view_title))
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