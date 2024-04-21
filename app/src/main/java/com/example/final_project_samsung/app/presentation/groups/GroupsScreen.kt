package com.example.final_project_samsung.app.presentation.groups

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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import com.example.final_project_samsung.R
import com.example.final_project_samsung.app.domain.model.Event
import com.example.final_project_samsung.app.domain.model.Group
import com.example.final_project_samsung.app.presentation.appNavigation.TheAppDestinations
import com.example.final_project_samsung.utils.timeFormatter
import java.time.LocalDateTime
import java.time.ZoneOffset


@Composable
fun GroupsScreen(
    groupViewModel: GroupsViewModel,
    groupsUiState: GroupsUiState,
    navController: NavController
) {
    DailyLayout(
        eventList = groupsUiState.eventList,
        groupList = groupsUiState.groupList,
        groupViewModel = groupViewModel,
        navController = navController
    )


}

@Composable
fun DailyLayout(
    modifier: Modifier = Modifier,
    eventList: List<Event>,
    groupList: List<Group>,
    groupViewModel: GroupsViewModel,
    navController: NavController
) {
    val masterHorizontalScrollState = rememberScrollState()

    val dayHeight = 1000.dp
    val gridWidth = 100.dp
    val gridHeight = dayHeight / 24f
    val groupBarHeight = 20.dp
    val timeBarWidth = 20.dp

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
                    eventList,
                    groupViewModel,
                    navController
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
        groupList.forEach { group ->
            Text(
                text = if (group.groupName == "") stringResource(id = R.string.no_name_group_default) else group.groupName,
                Modifier
                    .height(groupBarHeight)
                    .width(gridWidth)
                    .clickable {
                        navController.navigate(
                            TheAppDestinations.ADD_EDIT_GROUP_ROUTE +
                                    "?groupId=${group.id}"
                        )
                    })
        }
    }
}

@Composable
fun CanvasForDay(
    gridHeight: Dp,
    gridWidth: Dp,
    modifier: Modifier = Modifier,
    groupList: List<Group>
) {
    val color = MaterialTheme.colorScheme.outlineVariant

    Canvas(modifier = modifier.fillMaxSize()) {
        repeat(24) {
            val x = groupList.size * gridWidth.toPx()
            val y = it * gridHeight.toPx()
            drawLine(color, Offset(0f, y), Offset(x, y), 2f)
        }
        repeat(groupList.size + 1) {
            val x = (gridWidth * it).roundToPx().toFloat()
            drawLine(color, Offset(x, 0f), Offset(x, 25 * gridHeight.toPx()), 2f)
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
    eventList: List<Event>,
    groupViewModel: GroupsViewModel,
    navController: NavController
) {
    Layout(
        content = {
            GetContentForDay(
                yearAndDay,
                gridHeight,
                eventList,
                groupViewModel,
                navController
            )
        },
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
                val eventDataToPlace = listOfEventDataInDay[index]
                val y =
                    (eventDataToPlace.event.startTime.hour + eventDataToPlace.event.startTime.minute / 60f) * gridHeight
                val x = (eventDataToPlace.group?.positionInView ?: 0) * gridWidth
                placeable.placeRelative(x.roundToPx(), y.roundToPx())
                index++
            }
        }
    }
}

val listOfEventDataInDay = mutableListOf<EventDataToPlace>()

data class EventDataToPlace(
    val event: Event,
    val group: Group?
)

@Composable
fun GetContentForDay(
    yearAndDay: YearAndDay,
    gridHeight: Dp,
    eventDataList: List<Event>,
    groupViewModel: GroupsViewModel,
    navController: NavController
) {
//    eventDataList.forEach { event ->
//        event.groupId.forEachIndexed { _, groupId ->
//            if (event.startTime.year == yearAndDay.year && event.startTime.dayOfYear == yearAndDay.day) {
//                listOfEventDataInDay.add(
//                    EventDataToPlace(
//                        event,
//                        groupViewModel.getGroupById(groupId)
//                    )
//                )
//                EventCardBuilder(event, gridHeight, navController)
//            }
//        }
//    }
}

@Composable
fun EventCardBuilder(
    event: Event,
    gridHeight: Dp,
    navController: NavController
) {
    val eventHeight = max(
        gridHeight,
        (event.endTime.toEpochSecond(ZoneOffset.UTC) - event.startTime.toEpochSecond(ZoneOffset.UTC)).toInt() / (60f * 60f) * gridHeight
    )
    Card(modifier = Modifier
        .height(eventHeight)
        .clickable {
            navController.context
            navController.navigate(
                TheAppDestinations.ADD_EDIT_EVENT_ROUTE +
                        "?eventId=${event.eventId}"
            )
        }) {
        val startTime = event.startTime.toLocalTime().format(timeFormatter)
        val endTime = event.endTime.toLocalTime().format(timeFormatter)
        Text(text = if (event.eventName == "") stringResource(R.string.no_name_group_default) else event.eventName)
        Text(text = "$startTime - $endTime")
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