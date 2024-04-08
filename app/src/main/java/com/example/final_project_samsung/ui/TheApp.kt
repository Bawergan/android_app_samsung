package com.example.final_project_samsung.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.final_project_samsung.data.EventData
import com.example.final_project_samsung.data.GroupData
import com.example.final_project_samsung.data.listOfEventData
import com.example.final_project_samsung.data.listOfGroupData
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Locale

@Composable
fun TheApp(viewModel: MainViewModel = viewModel()) {
    var i = 0
    listOfEventData.forEach {
        it.groupsForEvent.add(listOfGroupData[i])
        i++
        i %= listOfGroupData.size
    }

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
            DailyLayout(
                eventDataList = listOfEventData,
                groupList = listOfGroupData,
            )
        }
        EditingModalBottomSheet(viewModel, appUiState)
    }
}

@Preview(name = "preview")
@Composable
fun TryNewLayoutPreview() {
    DailyLayout()
}

@Composable
fun DailyLayout(
    modifier: Modifier = Modifier,
    eventDataList: List<EventData> = listOfEventData,
    groupList: List<GroupData> = listOfGroupData,
) {
    val masterVerticalScrollState = rememberScrollState()
    val masterHorizontalScrollState = rememberScrollState()

    val dayHeight = 1000.dp
    val gridWidth = 100.dp
    val gridHeight = dayHeight / 25f
    val groupBarHeight = 20.dp
    val timeBarWidth = 20.dp
    Box(
        modifier = modifier
            .verticalScroll(masterVerticalScrollState)
            .height(dayHeight)
            .width(groupList.size * gridWidth)
            .padding(top = groupBarHeight)
    ) {
        val date = YearAndDay(
            LocalDateTime.parse("2024-04-07T15:30").year,
            LocalDateTime.parse("2024-04-07T15:30").dayOfYear
        )
        LayoutForDay(
            date,
            dayHeight,
            gridWidth,
            gridHeight,
            Modifier
                .horizontalScroll(masterHorizontalScrollState)
                .width(gridWidth * groupList.size + timeBarWidth)
                .padding(start = timeBarWidth),
            eventDataList,
            groupList
        )
        Row {
            Box(Modifier.width(timeBarWidth)) {
                repeat(24) {
                    Text(
                        text = it.toString(),
                        Modifier
                            .padding(top = it * gridHeight)
                            .fillMaxWidth()
                    )
                }
            }
        }
        val lineHeight = 2.dp
        Surface(
            Modifier.offset(y = (LocalDateTime.now().hour + LocalDateTime.now().minute / 60f) * gridHeight - lineHeight / 2),
            color = Color.Red
        ) {
            Box(
                modifier = Modifier
                    .height(lineHeight)
                    .width(groupList.size * gridWidth + timeBarWidth)
            )
        }
    }

    Row(
        Modifier
            .horizontalScroll(masterHorizontalScrollState)
            .padding(start = timeBarWidth)
    ) {
        groupList.forEach { group ->
            Text(
                text = group.groupTags[0],
                Modifier
                    .height(groupBarHeight)
                    .width(gridWidth)
            )
        }
    }
}

@Composable
fun CanvasForDay(gridHeight: Dp, gridWidth: Dp, modifier: Modifier, groupList: List<GroupData>) {
    Canvas(modifier = modifier.fillMaxSize()) {
        repeat(25) {
            val x = groupList.size * gridWidth.toPx()
            val y = it * gridHeight.toPx()
            drawLine(Color.LightGray, Offset(0f, y), Offset(x, y), 2f)
        }
        repeat(groupList.size) {
            val x = (gridWidth * it).roundToPx().toFloat()
            drawLine(Color.LightGray, Offset(x, 0f), Offset(x, 25 * gridHeight.toPx()), 2f)
        }
    }
}


@Composable
fun LayoutForDay(
    yearAndDay: YearAndDay,
    dayHeight: Dp,
    gridWidth: Dp,
    gridHeight: Dp,
    modifier: Modifier = Modifier,
    eventDataList: List<EventData>,
    groupList: List<GroupData>
) {
    CanvasForDay(gridHeight, gridWidth, modifier, groupList)
    Layout(
        content = { GetContentForDay(yearAndDay, gridHeight, eventDataList) }, modifier
    ) { measurables, constraints ->
        val itemConstraints =
            constraints.copy(maxWidth = gridWidth.roundToPx(), minWidth = gridWidth.roundToPx())
        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(itemConstraints)
            placeable
        }

        layout(constraints.maxWidth, dayHeight.roundToPx()) {
            var index = 0
            placeables.forEach { placeable ->
                val eventData = listOfEventDataInDay[index]
                val y = eventData.event.startTime.hour * gridHeight
                val x = eventData.group.positionInView * gridWidth

                placeable.place(x.roundToPx(), y.roundToPx())

                index++
            }
        }
    }
}

val listOfEventDataInDay = mutableListOf<EventDataToPlace>()

data class EventDataToPlace(val event: EventData, val group: GroupData)

@Composable
fun GetContentForDay(yearAndDay: YearAndDay, gridHeight: Dp, eventDataList: List<EventData>) {
    eventDataList.forEach { event ->
        event.groupsForEvent.forEach { group ->
            if (event.startTime.year == yearAndDay.year && event.startTime.dayOfYear == yearAndDay.day) {
                listOfEventDataInDay.add(EventDataToPlace(event, group))
                val eventHeight = (event.endTime.hour - event.startTime.hour) * gridHeight
                Card(modifier = Modifier
                    .height(eventHeight)
                    .clickable { }) {
                    Text(text = event.eventTags[0])
                    Text(text = event.startTime.hour.toString())
                }
            }
        }
    }
}

data class YearAndDay(val year: Int, val day: Int)

fun getListOfDays(now: LocalDateTime): List<YearAndDay> {
    val listOfYearAndDay = mutableListOf<YearAndDay>()
    for (i in -20..20) {
        listOfYearAndDay.add(
            YearAndDay(
                now.plusDays(i.toLong()).year,
                now.plusDays(i.toLong()).dayOfYear
            )
        )
    }
    return listOfYearAndDay.toList()
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