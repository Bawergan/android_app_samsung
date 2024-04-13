package com.example.final_project_samsung.ui.groups

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.final_project_samsung.R
import com.example.final_project_samsung.data.EventData
import com.example.final_project_samsung.data.GroupData
import java.time.LocalDateTime
import java.time.ZoneOffset


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(viewModel: GroupsViewModel, groupsUiState: GroupsUiState, openDrawer: () -> Unit) {
    val topAppBarState = rememberTopAppBarState()
    Scaffold(
        topBar = { GroupsTopAppBar(openDrawer = openDrawer, topAppBarState = topAppBarState) },
        floatingActionButton = {
            FloatingActionButtonManager(groupsUiState)
        }) {
        Box(modifier = Modifier.padding(it)) {
            DailyLayout(
                eventDataList = groupsUiState.eventList,
                groupList = groupsUiState.groupList, groupsUiState = groupsUiState
            )
        }
        GroupEditingBottomSheet(groupsUiState = groupsUiState, viewModel = viewModel)
        EventEditingBottomSheet(groupsUiState = groupsUiState, viewModel = viewModel)
    }
}

@Composable
fun DailyLayout(
    modifier: Modifier = Modifier,
    eventDataList: List<EventData>,
    groupList: List<GroupData>,
    groupsUiState: GroupsUiState,
) {
//    val masterVerticalScrollState = rememberScrollState()
    val masterHorizontalScrollState = rememberScrollState()

    val dayHeight = 1000.dp
    val gridWidth = 100.dp
    val gridHeight = dayHeight / 24f
    val groupBarHeight = 20.dp
    val timeBarWidth = 20.dp

//    val date = YearAndDay(
//        LocalDateTime.parse("2024-04-07T15:30").year,
//        LocalDateTime.parse("2024-04-07T15:30").dayOfYear
//    )
    val date = YearAndDay(
        LocalDateTime.now().year,
        LocalDateTime.now().dayOfYear
    )

    LazyColumn(
        state = LazyListState(firstVisibleItemIndex = 2),
        modifier = Modifier.horizontalScroll(state = masterHorizontalScrollState)
    ) {
        items(getListOfDays(LocalDateTime.now())) { itemDate ->
            Box(
                modifier = modifier
                    .height(dayHeight)
                    .width(groupList.size * gridWidth + timeBarWidth)
                    .padding(top = groupBarHeight)
            ) {

                CanvasForDay(
                    gridHeight,
                    gridWidth,
                    Modifier
                        .width(gridWidth * groupList.size + timeBarWidth)
                        .padding(start = timeBarWidth),
                    groupList
                )
                LayoutForDay(
                    itemDate,
                    dayHeight,
                    gridWidth,
                    gridHeight,
                    Modifier
                        .width(gridWidth * groupList.size + timeBarWidth)
                        .padding(start = timeBarWidth, top = groupBarHeight),
                    eventDataList,
                    groupsUiState
                )

                Column(modifier = Modifier.width(timeBarWidth)) {
                    repeat(24) {
                        Text(
                            text = it.toString(),
                            Modifier
                                .fillMaxWidth()
                                .height(gridHeight)
                        )
                    }
                }

                val lineHeight = 2.dp
                if (itemDate == date) {
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
            }
        }
    }

    Row(
        Modifier
            .horizontalScroll(masterHorizontalScrollState)
            .padding(start = timeBarWidth)
    ) {
        groupList.forEachIndexed { index, group ->
            Text(text = group.groupTags[0],
                Modifier
                    .height(groupBarHeight)
                    .width(gridWidth)
                    .clickable {
                        groupsUiState.isGroupEditingBottomSheetOpen.value = true
                        groupsUiState.chosenGroup.value = index
                    })
        }
    }
}

@Composable
fun CanvasForDay(
    gridHeight: Dp, gridWidth: Dp, modifier: Modifier = Modifier, groupList: List<GroupData>
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        repeat(24) {
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
    groupsUiState: GroupsUiState
) {
    Layout(
        content = { GetContentForDay(yearAndDay, gridHeight, eventDataList, groupsUiState) },
        modifier = modifier
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
                val y =
                    (eventData.event.startTime.hour + eventData.event.startTime.minute / 60f) * gridHeight
                val x = eventData.group.positionInView * gridWidth
                placeable.placeRelative(x.roundToPx(), y.roundToPx())
                index++
            }
        }
    }
}

val listOfEventDataInDay = mutableListOf<EventDataToPlace>()

data class EventDataToPlace(val event: EventData, val group: GroupData)

@Composable
fun GetContentForDay(
    yearAndDay: YearAndDay,
    gridHeight: Dp,
    eventDataList: List<EventData>,
    groupsUiState: GroupsUiState
) {
    eventDataList.forEach { event ->
        event.groupsForEvent.forEachIndexed { index, group ->
            if (event.startTime.year == yearAndDay.year && event.startTime.dayOfYear == yearAndDay.day) {
                listOfEventDataInDay.add(EventDataToPlace(event, group))
                EventCardBuilder(event, gridHeight, groupsUiState, index)
            }
        }
    }
}

@Composable
fun EventCardBuilder(event: EventData, gridHeight: Dp, groupsUiState: GroupsUiState, index: Int) {
    val eventHeight =
        (event.endTime.toEpochSecond(ZoneOffset.UTC) - event.startTime.toEpochSecond(ZoneOffset.UTC)).toInt() / (60f * 60f) * gridHeight

    Card(modifier = Modifier
        .height(eventHeight)
        .clickable { groupsUiState.chosenEvent.value = index }) {
        Text(text = event.eventTags[0])
        Text(text = event.startTime.toLocalTime().toString())
        Text(text = event.endTime.toLocalTime().toString())
    }
}

data class YearAndDay(val year: Int, val day: Int)

fun getListOfDays(now: LocalDateTime): List<YearAndDay> {
    val listOfYearAndDay = mutableListOf<YearAndDay>()
    for (i in -2..2) {
        listOfYearAndDay.add(
            YearAndDay(
                now.plusDays(i.toLong()).year, now.plusDays(i.toLong()).dayOfYear
            )
        )
    }
    return listOfYearAndDay.toList()
}

@Composable
fun FloatingActionButtonManager(groupsUiState: GroupsUiState) {
    if (groupsUiState.isMainActionButtonClicked.value) {
        Column {
            Button(onClick = {
                groupsUiState.isGroupEditingBottomSheetOpen.value = true
                groupsUiState.isMainActionButtonClicked.value = false
            }) {
                Text(text = "New Group")
            }
            Button(onClick = {
                groupsUiState.isEventEditingBottomSheetOpen.value = true
                groupsUiState.isMainActionButtonClicked.value = false
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
fun GroupEditingBottomSheet(
    groupsUiState: GroupsUiState, viewModel: GroupsViewModel
) {
    if (groupsUiState.isGroupEditingBottomSheetOpen.value) {
        ModalBottomSheet(onDismissRequest = {
            groupsUiState.isGroupEditingBottomSheetOpen.value = false
            groupsUiState.chosenGroup.value = null
        }) {

            val group = if (groupsUiState.chosenGroup.value != null) {
                groupsUiState.groupList[groupsUiState.chosenGroup.value!!]
            } else {
                null
            }


            val res = stringResource(R.string.no_name_group_default)
            var newName by remember { mutableStateOf(group?.groupTags?.get(0) ?: "") }
            if (newName == res) {
                newName = ""
            }

            Column {
                Button(onClick = {
                    if (newName == "") {
                        newName = res
                    }
                    if (group == null) {
                        groupsUiState.groupList.add(viewModel.groupList.getNewGroupData(newName))
                    } else {
//                        group.groupTags[0] = newName
                        groupsUiState.groupList[groupsUiState.chosenGroup.value!!].groupTags[0] =
                            newName
                    }
                    groupsUiState.chosenGroup.value = null
                    groupsUiState.isGroupEditingBottomSheetOpen.value = false
                    viewModel.updateListOfEventCard()
                }) { Text(text = "Save") }

                TextField(
                    value = newName,
                    onValueChange = { newName = it },
                    placeholder = {
                        Text(text = "Add title")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventEditingBottomSheet(
    groupsUiState: GroupsUiState, viewModel: GroupsViewModel
) {
    if (groupsUiState.isEventEditingBottomSheetOpen.value) {
        ModalBottomSheet(onDismissRequest = {
            groupsUiState.isEventEditingBottomSheetOpen.value = false
            groupsUiState.chosenEvent.value = null
        }) {

            val event = if (groupsUiState.chosenEvent.value != null) {
                groupsUiState.eventList[groupsUiState.chosenEvent.value!!]
            } else {
                null
            }


            val res = stringResource(R.string.no_name_event_default)
            var newName by remember { mutableStateOf(event?.eventTags?.get(0) ?: "") }
            if (newName == res) {
                newName = ""
            }

            Column {
                Button(onClick = {
                    if (newName == "") {
                        newName = res
                    }
                    if (event == null) {
                        groupsUiState.eventList.add(viewModel.eventList.getNewEventData(newName))
                    } else {
//                        group.groupTags[0] = newName
                        groupsUiState.eventList[groupsUiState.chosenEvent.value!!].eventTags[0] =
                            newName
                    }
                    groupsUiState.chosenEvent.value = null
                    groupsUiState.isEventEditingBottomSheetOpen.value = false
                    viewModel.updateListOfEventCard()
                }) { Text(text = "Save") }

                TextField(
                    value = newName,
                    onValueChange = { newName = it },
                    placeholder = {
                        Text(text = "Add title")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
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
//    val context = LocalContext.current
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