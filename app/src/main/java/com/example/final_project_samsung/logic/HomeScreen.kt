package com.example.final_project_samsung.logic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    val eventViewModel = MainViewModel()
    val appUiState by eventViewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            if (appUiState.activeEventId.value == null) {
                LargeFloatingActionButton(onClick = { eventViewModel.addEventStart() }) {
                    Icon(
                        Icons.Filled.Add,
                        "add time stamp"
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            LazyColumn() {
                items(appUiState.eventList) { ItemEvent(appUiState, it) }
            }
        }
        if (appUiState.activeEventId.value != null) {
            DateBottomSheet(eventViewModel, appUiState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateBottomSheet(eventViewModel: MainViewModel, appUiState: AppUiState) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        sheetContent = {
            Text(text = "${appUiState.activeEventId.value}")
            Row(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    eventViewModel.deleteEvent(appUiState.activeEventId.value!!)
                    appUiState.activeEventId.value = null
                }) {
                    Icon(
                        Icons.Filled.Delete,
                        "Delete event"
                    )
                }
                Button(onClick = {
                    eventViewModel.editEvent(appUiState.activeEventId.value!!)
                    appUiState.activeEventId.value = null
                }) {
                    Icon(
                        Icons.Filled.Edit,
                        "Edit event"
                    )
                }
                Button(onClick = {
                    eventViewModel.endEvent(appUiState.activeEventId.value!!)
                    appUiState.activeEventId.value = null
                }) {
                    Icon(
                        Icons.Filled.Done,
                        "End event"
                    )
                }
            }
        },
        scaffoldState = bottomSheetScaffoldState,
//        sheetPeekHeight = 0.dp,
        sheetDragHandle = { false }
    ) {

    }
}

@Composable
fun ItemEvent(appUiState: AppUiState, event: EventData) {
    Card(
        Modifier
            .padding(7.dp)
            .clickable { appUiState.activeEventId.value = event.id }
    ) {
        Column(modifier = Modifier.padding(7.dp)) {
            Text(
                text = "id: ${event.id}",
            )
            Text(text = "started at ${event.startTime}")
            if ("ended" in event.tagsEvent) {
                Text(text = "ended at ${event.endTime}")
            }
            Spacer(Modifier.fillMaxHeight())
            Text(text = "tags: ${event.tagsEvent}")
        }
    }
}