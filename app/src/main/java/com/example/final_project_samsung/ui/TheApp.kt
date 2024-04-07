package com.example.final_project_samsung.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_project_samsung.data.EventData
import com.example.final_project_samsung.data.GroupData
import com.example.final_project_samsung.data.listOfEventData
import com.example.final_project_samsung.data.listOfGroupData
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TheApp(viewModel: MainViewModel = viewModel()) {
    if (viewModel.groupList.groupList.size == 0) {
        viewModel.groupList.addNewGroup()
    }
    val appUiState by viewModel.uiState.collectAsState()
    MainUIScreenLayout(viewModel, appUiState)
}

@Composable
fun MainUIScreenLayout(viewModel: MainViewModel, appUiState: AppUiState) {
    Scaffold(floatingActionButton = {
        FloatingActionColumnOfButtons(viewModel, appUiState)
    }) {
        Box(modifier = Modifier.padding(it)) {
//            GroupsAndEventsLazyView(viewModel, appUiState)
            TryNewLayout(eventDataList = listOfEventData, groupList = listOfGroupData)
        }
        EditingModalBottomSheet(viewModel, appUiState)
    }
}

@Preview(name = "preview")
@Composable
fun TryNewLayoutPreview() {
    TryNewLayout(eventDataList = listOfEventData, groupList = listOfGroupData)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TryNewLayout(
    eventDataList: List<EventData>, groupList: List<GroupData>, modifier: Modifier = Modifier
) {
    val masterVerticalScrollState = rememberScrollState()
    val masterHorizontalScrollState = rememberScrollState()

    val dayHeight = 1000.dp
    val gridWidth = 100.dp
    val gridHeight = dayHeight / 23f
    val groupBarHeight = 20.dp
    val timeBarWidth = 20.dp

    Box(
        Modifier
            .verticalScroll(masterVerticalScrollState)
            .width(timeBarWidth)
            .padding(top = groupBarHeight)
    ) {
        repeat(24) {
            Text(
                text = it.toString(), Modifier.padding(top = it * gridHeight)
            )
        }
    }
    Row(Modifier.horizontalScroll(masterHorizontalScrollState)) {
        Spacer(modifier = Modifier.width(timeBarWidth))
        for (group in groupList) {
            Text(
                text = group.groupTags[0],
                Modifier
                    .height(groupBarHeight)
                    .width(gridWidth)
            )
        }
    }
    Box(
        modifier = modifier
            .verticalScroll(masterVerticalScrollState)
            .horizontalScroll(masterHorizontalScrollState)
            .height(dayHeight)
    ) {
        Row {
            Spacer(modifier = Modifier.width(timeBarWidth))
            for (group in groupList) {
                Box(Modifier.width(gridWidth)) {
                    for (event in group.eventsInGroup) {
                        val eventPadding = event.startTime.hour * gridHeight
                        val eventHeight = (event.endTime.hour - event.startTime.hour) * gridHeight
                        Card(modifier = Modifier
                            .padding(top = eventPadding + groupBarHeight)
                            .height(eventHeight)
                            .clickable { }
                            .fillMaxWidth()) {
                            Text(text = event.eventTags[0])
                            Text(text = event.startTime.hour.toString())
                            Text(text = eventPadding.toString())
                        }
                    }
                }
            }
        }


//        Column(
//            modifier = modifier
//                .verticalScroll(rememberScrollState())
//                .statusBarsPadding()
//        ) {
//            EventVerticalList() {
//                eventDataList.forEach { event -> EventCard(event) }
//            }
//        }
    }
}


@Composable
fun EventCard(event: EventData) {
    Surface {
        Text(text = event.id.toString())
        Text(text = event.eventTags[0])
        Text(text = event.startTime.toString())
    }
}

private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}

@Composable
fun EventVerticalList(
    modifier: Modifier = Modifier, content: @Composable () -> Unit
) {
    Layout(content = content) { measurables, constraints ->
        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
        val maxColumnWidth = 200.dp
//        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columns = listOfGroupData.size
//        val columnWidth = constraints.maxWidth / columns
        val columnWidth = maxColumnWidth.toPx().toInt()
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(columns) { 0 } // track each column's height
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight

        layout(
            width = constraints.maxWidth, height = height
        ) {
            val colY = IntArray(columns) { 0 }
            placeables.forEach { placeable ->
                val column = shortestColumn(colY)
                placeable.place(
                    x = columnWidth * column, y = colY[column]
                )
                colY[column] += placeable.height
            }
        }
    }
}


@Composable
fun FloatingActionColumnOfButtons(viewModel: MainViewModel, appUiState: AppUiState) {
    Column(horizontalAlignment = AbsoluteAlignment.Right) {
        if (appUiState.activeEventCard.value != null) {
            val activeEventCard = appUiState.activeEventCard.value!!

            FloatingActionButton(onClick = {
                viewModel.eventList.deleteEvent(activeEventCard.eventId)
                viewModel.updateListOfEventCard()
                appUiState.activeEventCard.value = null
            }) { Text(text = "Delete") }
            Spacer(modifier = Modifier.size(5.dp))
            FloatingActionButton(onClick = {
                appUiState.isInEditMode.value = true
            }) { Text(text = "Edit") }
            Spacer(modifier = Modifier.size(5.dp))
            FloatingActionButton(onClick = {
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
                        "dd.MM.yyy; HH:mm", Locale.US
                    ).format(activeEvent.startTime)
                )
                Button(onClick = {
                    viewModel.eventList.addTagToEvent(activeEvent.id, newTag)
                    viewModel.updateListOfEventCard()
                }) { Text(text = "Add tag") }
                TextField(modifier = Modifier.fillMaxWidth(),
                    value = newTag,
                    onValueChange = { newTag = it },
                    placeholder = { Text(text = "Add tag") })
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
            Text(text = "${eventCard.tag} at ${eventCard.time}:${eventCard.time}")
        }
    }
}