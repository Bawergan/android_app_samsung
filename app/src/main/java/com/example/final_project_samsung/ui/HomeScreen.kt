package com.example.final_project_samsung.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HomeScreen(viewModel: MainViewModel = viewModel()) {
    val appUiState by viewModel.uiState.collectAsState()
    MainUIScreen(viewModel, appUiState)
}

@Composable
fun MainUIScreen(viewModel: MainViewModel, appUiState: AppUiState){
    Scaffold(
        floatingActionButton = {
            FloatingActionColumnOfButtons(viewModel, appUiState)
        }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            LazyColumn {
                items(appUiState.eventCardList) { ItemEvent(appUiState, it) }
            }
        }
    }
}

@Composable
fun FloatingActionColumnOfButtons(viewModel: MainViewModel, appUiState: AppUiState) {
    Column(horizontalAlignment = AbsoluteAlignment.Right) {
        if (appUiState.activeEventCard.value != null) {
            val activeEventCard = appUiState.activeEventCard.value!!

            FloatingActionButton(
                onClick = {
                    viewModel.deleteEvent(activeEventCard.eventId)
                    appUiState.activeEventCard.value = null
                }) { Text(text = "Delete") }
            Spacer(modifier = Modifier.size(5.dp))
            FloatingActionButton(
                onClick = {
//                    viewModel.editEvent(activeEventCard.eventId, newName)
                    appUiState.activeEventCard.value = null
                }) { Text(text = "Edit") }
            Spacer(modifier = Modifier.size(5.dp))
            FloatingActionButton(
                onClick = {
                    viewModel.endEvent(activeEventCard.eventId)
                    appUiState.activeEventCard.value = null
                }) { Text(text = "End") }
            Spacer(modifier = Modifier.size(10.dp))
        }

        LargeFloatingActionButton(onClick = { viewModel.addEventStart() }) {
            Icon(
                Icons.Filled.Add, "add time stamp"
            )
        }
    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateBottomSheet(eventViewModel: MainViewModel, appUiState: AppUiState) {
    val activeEventCard = appUiState.activeEventCard.value!!
    var newName by remember { mutableStateOf("") }
    if (activeEventCard.name != "(No title)") {
        newName = activeEventCard.name
    }
    BottomSheetScaffold(
        sheetContent = {
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
    ) {}
}