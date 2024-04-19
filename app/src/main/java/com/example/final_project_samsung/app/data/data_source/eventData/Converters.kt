package com.example.final_project_samsung.app.data.data_source.eventData

import com.example.final_project_samsung.app.data.data_source.jsonToList
import com.example.final_project_samsung.app.data.data_source.listOfStringToJson
import com.example.final_project_samsung.app.domain.model.Event
import java.time.LocalDateTime

fun EntityEvent.toEvent(): Event {
    return Event(
        this.eventName,
        jsonToList(this.eventTags).toMutableList(),
        LocalDateTime.parse(this.startTime),
        LocalDateTime.parse(this.endTime),
        this.groupId,
        this.eventId
    )
}

fun Event.toEntity(): EntityEvent {
    return EntityEvent(
        this.eventName,
        listOfStringToJson(this.eventTags),
        this.startTime.toString(),
        this.endTime.toString(),
        this.groupId,
        this.id
    )

}
