package com.example.final_project_samsung.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ModalBottomSheet
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
import com.example.final_project_samsung.data.EventData
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HomeScreen(viewModel: MainViewModel = viewModel()) {
    if (viewModel.groupList.groupList.size == 0) {
        viewModel.groupList.addNewGroup()
    }
    val appUiState by viewModel.uiState.collectAsState()
    MainUIScreenLayout(viewModel, appUiState)
}

@Composable
fun MainUIScreenLayout(viewModel: MainViewModel, appUiState: AppUiState) {
    Scaffold(
        floatingActionButton = {
            FloatingActionColumnOfButtons(viewModel, appUiState)
        }) {
        Box(modifier = Modifier.padding(it)) {
            GroupsAndEventsLazyView(viewModel, appUiState)
        }
        EditingModalBottomSheet(viewModel, appUiState)
    }
}

@Composable
fun GroupsAndEventsLazyView(viewModel: MainViewModel, appUiState: AppUiState) {
    LazyRow {
        items(viewModel.groupList.groupList) {
            val currentGroup = it
            LazyColumn {
                items(appUiState.eventCardList) { cardEventData ->
                    if (cardEventData.eventId in currentGroup.eventsInGroupIds) {
                        ItemEvent(appUiState, cardEventData)
                    }
                }
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
                    viewModel.eventList.deleteEvent(activeEventCard.eventId)
                    viewModel.updateListOfEventCard()
                    appUiState.activeEventCard.value = null
                }) { Text(text = "Delete") }
            Spacer(modifier = Modifier.size(5.dp))
            FloatingActionButton(
                onClick = {
                    appUiState.isInEditMode.value = true
                }) { Text(text = "Edit") }
            Spacer(modifier = Modifier.size(5.dp))
            FloatingActionButton(
                onClick = {
                    viewModel.eventList.endEvent(activeEventCard.eventId)
                    viewModel.updateListOfEventCard()
                    appUiState.activeEventCard.value = null
                }) { Text(text = "End") }
            Spacer(modifier = Modifier.size(10.dp))
        }

        LargeFloatingActionButton(onClick = {
            val newEvent = EventData(viewModel.eventList.getNewId())
            viewModel.eventList.addEventToList(newEvent)
            viewModel.addEventToGroup(newEvent.id, viewModel.groupList.groupList[0].id)
            viewModel.updateListOfEventCard()
        }) {
            Icon(
                Icons.Filled.Add, "add time stamp"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditingModalBottomSheet(viewModel: MainViewModel, appUiState: AppUiState) {
    if (appUiState.isInEditMode.value && appUiState.activeEventCard.value != null) {
        val activeEvent =
            viewModel.eventList.getEventWithId(appUiState.activeEventCard.value!!.eventId)
        ModalBottomSheet(onDismissRequest = {
            appUiState.isInEditMode.value = false
            appUiState.activeEventCard.value = null
        }) {
            var newName by remember { mutableStateOf("") }
            if (activeEvent.eventTags[0] != "(No title)") {
                newName = activeEvent.eventTags[0]

            }

            var newTag by remember { mutableStateOf("") }

            Column {
                Button(onClick = {
                    viewModel.eventList.editEventName(activeEvent.id, newName)
                    appUiState.isInEditMode.value = false
                    viewModel.updateListOfEventCard()
                }) { Text(text = "Save") }
                TextField(modifier = Modifier.fillMaxWidth(),
                    value = newName,
                    onValueChange = { newName = it },
                    placeholder = { Text(text = "Add title") })
                Text(
                    text = SimpleDateFormat(
                        "dd.MM.yyy; HH:mm",
                        Locale.US
                    ).format(activeEvent.startTime)
                )
                LazyVerticalGrid(columns = GridCells.Adaptive(100.dp)) {
                    items(viewModel.eventList.getEventWithId(activeEvent.id).eventGroups) {
                        Text(text = it)
                    }
                }
                Button(onClick = {
                    viewModel.eventList.addTagToEvent(activeEvent.id, newTag)
                    viewModel.updateListOfEventCard()
                }) { Text(text = "Add tag") }
                TextField(modifier = Modifier.fillMaxWidth(),
                    value = newTag,
                    onValueChange = { newTag = it },
                    placeholder = { Text(text = "Add tag") }
                )
            }
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