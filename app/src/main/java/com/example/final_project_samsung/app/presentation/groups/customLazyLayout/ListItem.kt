package com.example.final_project_samsung.app.presentation.groups.customLazyLayout

import com.example.final_project_samsung.app.domain.model.Event
import com.example.final_project_samsung.app.domain.model.Group

data class ListItem(
    val fromX: Int,
    val fromY: Int,
    val toX: Int,
    val toY: Int,
    val dataArray: Map<DataType, String>?,
    val itemType: ItemType
)

enum class ItemType {
    Event, Line, Group
}

enum class DataType {
    Name, StartTime, EndTime, Id, GroupPosition
}

fun Event.toMap(): Map<DataType, String> {
    return mapOf(
        Pair(
            DataType.Name,
            this.eventName
        ),
        Pair(
            DataType.StartTime,
            this.startTime.toString()
        ),
        Pair(
            DataType.EndTime,
            this.endTime.toString()
        ),
        Pair(DataType.Id, this.eventId.toString()),

        )
}

fun Group.toMap(): Map<DataType, String> {
    return mapOf(
        Pair(DataType.Name, this.groupName),
        Pair(DataType.Id, this.id.toString()),
        Pair(DataType.GroupPosition, this.positionInView.toString())
    )
}