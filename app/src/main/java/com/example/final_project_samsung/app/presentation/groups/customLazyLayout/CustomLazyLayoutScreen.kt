package com.example.final_project_samsung.app.presentation.groups.customLazyLayout

import android.content.res.Resources
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import androidx.navigation.NavController
import com.example.final_project_samsung.app.domain.model.Event
import com.example.final_project_samsung.app.presentation.appNavigation.TheAppDestinations
import com.example.final_project_samsung.app.presentation.groups.GroupsUiState
import com.example.final_project_samsung.app.presentation.groups.customLazyLayout.layout.CustomLazyLayout
import com.example.final_project_samsung.app.presentation.groups.customLazyLayout.layout.rememberLazyLayoutState
import com.example.final_project_samsung.utils.timeFormatter
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.math.roundToInt

@Composable
fun CustomLazyLayoutScreen(state: GroupsUiState, navController: NavController) {
    val lazyLayoutState = rememberLazyLayoutState()
    val color = MaterialTheme.colorScheme.outlineVariant
    state._currentTime.value = LocalDateTime.now()
    val cllWidth =
        ((state.groupList.size - (DISPLAY_RESOLUTION[1] / state.pdForGroup.toFloat())) * state.pdForGroup)

    Canvas(modifier = Modifier.fillMaxSize()) {
        val offsetForOffset = (state._currentTime.value.minute / 60f) * state.pdForHour
        var offset = lazyLayoutState.offsetState.value.toOffset()
        val hours = (DISPLAY_RESOLUTION[0] / state.pdForHour.toFloat()).roundToInt()
        val groups = (DISPLAY_RESOLUTION[1] / state.pdForGroup.toFloat()).roundToInt()
        offset = Offset(
            offset.x % (state.groupList.size * state.pdForGroup).toFloat(),
            (offset.y % (state.pdForHour).toFloat()) + state.pdForHour + offsetForOffset
        )

        repeat(hours + 1) {
            val x = (state.groupList.size * state.pdForGroup * DISPLAY_DENSITY)
            val y = (it * state.pdForHour * DISPLAY_DENSITY)
            drawLine(
                color,
                start = Offset(0f, y) - offset,
                end = Offset(x, y) - offset,
                2f
            )
        }
        repeat(state.groupList.size + 1) {
            val x = (state.pdForGroup * it * DISPLAY_DENSITY)
            drawLine(
                color,
                start = Offset(x, 0f) - offset,
                end = Offset(x, (25 * state.pdForHour * DISPLAY_DENSITY)) - offset,
                2f
            )
        }
    }
    CustomLazyLayout(
        state = lazyLayoutState,
        modifier = Modifier.fillMaxSize(),
        cllWidth = cllWidth
    ) {
        items(state.items) { item ->
            when (item.itemType) {
                ItemType.Event -> {
                    Card(
                        onClick = {
                            navController.navigate(
                                TheAppDestinations.ADD_EDIT_EVENT_ROUTE + "?eventId=${
                                    item.dataArray?.get(
                                        DataType.Id
                                    )?.toInt()
                                }"
                            )
                        },
                        modifier = Modifier.requiredSize(
                            width = (state.pdForGroup).dp,
                            height = ((item.toY - item.fromY)).dp
                        )
                    ) {
                        item.dataArray?.let { EventCard(it, state.pdForHour) }
                    }
                }

                else -> {}
            }
        }
        item(
            TIME_LINE.copy(
                fromY = ((state._currentTime.value
                    .toEpochSecond(ZoneOffset.UTC) / (60f * 60f)) * state.pdForHour).toInt() - state.offsetForCurrentTime,
                toX = (state.groupList.size * state.pdForGroup)

            )
        ) {
            TimeLineCard(state.groupList.size, state.pdForGroup)
        }
        item(GROUP_BAR) {
            Row(Modifier.background(MaterialTheme.colorScheme.background)) {
                state.groupList.forEach { group ->

                    Box(modifier = Modifier
                        .clickable {
                            navController.navigate(
                                TheAppDestinations.ADD_EDIT_GROUP_ROUTE + "?groupId=${
                                    group.id
                                }"
                            )
                        }

                        .width((state.pdForGroup).dp)
                    ) {
                        GroupItem(
                            group.groupName
                        )
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun GroupItem(groupName: String = "PreviewGroup", modifier: Modifier = Modifier) {
    Text(text = groupName, modifier = modifier, style = MaterialTheme.typography.labelSmall)
}

@Preview
@Composable
fun TimeLineCard(groupNumber: Int = 1, pdForGroup: Int = GroupsUiState().pdForGroup) {
    val lineHeight = 2.dp
    Surface(
        Modifier.offset(y = lineHeight / 2),
        color = Color.Red
    ) {
        Box(
            modifier = Modifier
                .height(lineHeight)
                .width((groupNumber * pdForGroup).dp)
        )
    }
}


@Preview
@Composable
fun EventCard(
    data: Map<DataType, String> = PREVIEW_EVENT.toMap(),
    pdForHour: Int = GroupsUiState().pdForHour
) {
    val startTime = LocalDateTime.parse(data[DataType.StartTime])
    val endTime = LocalDateTime.parse(data[DataType.EndTime])
    Box {
        Column {
            Text(text = data[DataType.Name].toString())
            Text(
                text = startTime.format(timeFormatter) + " - " + endTime.format(timeFormatter)
            )
        }
    }
}

val DISPLAY_DENSITY = Resources.getSystem().displayMetrics.density
val DISPLAY_RESOLUTION = listOf(
    Resources.getSystem().displayMetrics.heightPixels * DISPLAY_DENSITY,
    Resources.getSystem().displayMetrics.widthPixels * DISPLAY_DENSITY
)

val PREVIEW_EVENT = Event(
    eventName = "Preview",
    eventTags = emptyList(),
    eventId = 0,
    startTime = LocalDateTime.now(),
    endTime = LocalDateTime.now(),
    groupId = 0
)

val TIME_LINE = ListItem(
    fromX = 0,
    fromY = 0,
    toX = 0,
    toY = 2 * DISPLAY_DENSITY.toInt(),
    dataArray = null,
    itemType = ItemType.Line
)

val GROUP_BAR = ListItem(
    fromX = 0,
    fromY = 0,
    toX = 1,
    toY = 1,
    dataArray = null,
    itemType = ItemType.Group,

    )