package com.example.final_project_samsung.logic

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun HomeScreen() {
    val eventViewModel = MainViewModel()
    val appUiState by eventViewModel.uiState.collectAsState()

//    val eventDataList = remember { mutableListOf(EventData(0)).toMutableStateList() }
//    var activeEventId by rememberSaveable { mutableStateOf<Int?>(null) }

    Scaffold(
        floatingActionButton = {
            LargeFloatingActionButton(onClick = { eventViewModel.addEventStart() }) {
                Icon(
                    Icons.Filled.Add,
                    "add time stamp"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            LazyColumn {
                items(appUiState.eventList) {
                    Text(
                        text = "${it.id}, ${it.endTime}, ${it.tagsEvent}",
                        Modifier.clickable { appUiState.activeEventId.value = it.id })
                }
            }
        }
        if (appUiState.activeEventId.value != null) {
            ConstraintLayout(Modifier.fillMaxSize()) {
                val card = createRef()
                Card(
                    Modifier
                        .constrainAs(card) { bottom.linkTo(parent.bottom) }
                        .fillMaxWidth()) {
                    Row(Modifier.padding(8.dp)) {
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
                }
            }
        }
    }
}