package com.example.final_project_samsung.app.presentation.groups

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.final_project_samsung.app.domain.model.Event
import com.example.final_project_samsung.app.presentation.groups.customLazyLayout.DataType
import com.example.final_project_samsung.app.presentation.groups.customLazyLayout.toMap
import java.time.LocalDateTime

@Preview
@Composable
fun EventCard(data: Map<DataType, String> = PREVIEW_EVENT.toMap()) {
    Box {
        Column {
            Text(text = data[DataType.Name].toString())
            Text(text = data[DataType.StartTime] + " - " + data[DataType.EndTime])
        }
    }
}

val PREVIEW_EVENT = Event(
    eventName = "Preview",
    eventTags = emptyList(),
    eventId = 0,
    startTime = LocalDateTime.now(),
    endTime = LocalDateTime.now(),
    groupId = 0
)