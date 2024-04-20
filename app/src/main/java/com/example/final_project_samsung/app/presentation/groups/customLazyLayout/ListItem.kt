package com.example.final_project_samsung.app.presentation.groups.customLazyLayout

import com.example.final_project_samsung.app.domain.model.Event
import com.example.final_project_samsung.utils.timeFormatter

data class ListItem(
    val x: Int,
    val y: Int,
    val dataArray: Map<DataType, String>?,
    val itemType: ItemType
)

enum class ItemType {
    Event, Line
}

enum class DataType {
    Name, StartTime, EndTime, Id
}

fun Event.toMap(): Map<DataType, String> {
    return mapOf(
        Pair(
            DataType.Name,
            this.eventName
        ),
        Pair(
            DataType.StartTime,
            this.startTime.format(timeFormatter)
        ),
        Pair(
            DataType.EndTime,
            this.endTime.format(timeFormatter)
        ),
        Pair(DataType.Id, this.eventId.toString()),

        )
}