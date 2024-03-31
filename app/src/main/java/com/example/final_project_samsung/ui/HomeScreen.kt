package com.example.final_project_samsung.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetDefaults
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
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HomeScreen(viewModel: MainViewModel = viewModel()) {
    val appUiState by viewModel.uiState.collectAsState()
    Scaffold(floatingActionButton = {
        if (appUiState.activeEventCard.value == null) {
            LargeFloatingActionButton(onClick = { viewModel.addEventStart() }) {
                Icon(
                    Icons.Filled.Add, "add time stamp"
                )
            }
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            LazyColumn {
                items(appUiState.eventCardList) { ItemEvent(appUiState, it) }
            }
        }
        if (appUiState.activeEventCard.value != null) {
            DateBottomSheet(viewModel, appUiState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateBottomSheet(eventViewModel: MainViewModel, appUiState: AppUiState) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val activeEventCard = appUiState.activeEventCard.value!!
    var newName by remember { mutableStateOf("") }
    if (activeEventCard.name != "(No title)") {
        newName = activeEventCard.name
    }
    BottomSheetScaffold(
        sheetContent = {
            Row(
                modifier = Modifier
                    .height(60.dp)
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxHeight(),
                    onClick = {
                        eventViewModel.deleteEvent(activeEventCard.eventId)
                        appUiState.activeEventCard.value = null
                    }) {
//                    Icon(
//                        Icons.Filled.Delete,
//                        "Delete event"
//                    )
                    Text(text = "Delete")
                }
                Button(
                    modifier = Modifier.fillMaxHeight(),
                    onClick = {
                        eventViewModel.editEvent(activeEventCard.eventId, newName)
                        appUiState.activeEventCard.value = null
                    }) {
//                    Icon(
//                        Icons.Filled.Edit,
//                        "Edit event"
//                    )
                    Text(text = "Save")
                }
                Button(
                    modifier = Modifier.fillMaxHeight(),
                    onClick = {
                        eventViewModel.endEvent(activeEventCard.eventId)
                        appUiState.activeEventCard.value = null
                    }) {
//                    Icon(
//                        Icons.Filled.Done,
//                        "End event"
//                    )
                    Text(text = "End")
                }
            }
            TextField(modifier = Modifier.fillMaxWidth(),
                value = newName,
                onValueChange = { newName = it },
                placeholder = { Text(text = "Add title") })
            Text(
                text = SimpleDateFormat(
                    "dd.MM.yyy; HH:mm",
                    Locale.US
                ).format(activeEventCard.time)
            )
        },
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = BottomSheetDefaults.SheetPeekHeight + 60.dp, //128.dp,
        sheetDragHandle = {
            BottomSheetDefaults.DragHandle(
                height = 5.dp,
                width = 60.dp,
            )
        },
    ) {}
}

@Composable
fun ItemEvent(appUiState: AppUiState, eventCard: CardEventData) {
    Card(
        Modifier
            .padding(7.dp)
            .clickable { appUiState.activeEventCard.value = eventCard }) {
        Column(modifier = Modifier.padding(7.dp)) {
            Text(text = eventCard.name)
            Text(text = "${eventCard.tag} at ${eventCard.time.hours}:${eventCard.time.minutes}")
        }
    }
}