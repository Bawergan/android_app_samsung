package com.example.final_project_samsung.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(viewModel: MainViewModel = viewModel()) {
    val appUiState by viewModel.uiState.collectAsState()
    Scaffold(
        floatingActionButton = {
            if (appUiState.activeEventId.value == null) {
                LargeFloatingActionButton(onClick = { viewModel.addEventStart() }) {
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
            LazyColumn {
                items(appUiState.eventCardList) { ItemEvent(appUiState, it) }
            }
        }
        if (appUiState.activeEventId.value != null) {
            DateBottomSheet(viewModel, appUiState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateBottomSheet(eventViewModel: MainViewModel, appUiState: AppUiState) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    var newName by remember { mutableStateOf("") }

    BottomSheetScaffold(
        sheetPeekHeight = 128.dp,
        sheetContent = {
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
                    eventViewModel.editEvent(appUiState.activeEventId.value!!, newName)
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
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = newName,
                onValueChange = { newName = it },
                placeholder = { Text(text = "Name") })
        },
        scaffoldState = bottomSheetScaffoldState,
        sheetDragHandle = { }
    ) {

    }
}

@Composable
fun ItemEvent(appUiState: AppUiState, event: CardEventData) {
    Card(
        Modifier
            .padding(7.dp)
            .clickable { appUiState.activeEventId.value = event.eventId }
    ) {
        Column(modifier = Modifier.padding(7.dp)) {
            Text(text = "id: ${event.eventId}, name: ${event.name}")
            Text(text = "${event.tag} at ${event.time}")
        }
    }
}