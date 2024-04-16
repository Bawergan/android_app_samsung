package com.example.final_project_samsung.app.data.data_source

import com.example.final_project_samsung.app.domain.model.Event
import com.google.gson.Gson
import java.time.LocalDateTime

fun EntityEvent.toEvent(): Event {
    return Event(
        jsonToList(this.eventTags).toMutableList(),
        LocalDateTime.parse(this.startTime),
        LocalDateTime.parse(this.endTime),
        jsonToList(this.groupsIds).map { it.toInt() }.toMutableList(),
        this.id
    )
}

fun Event.toEntity(): EntityEvent {
    return EntityEvent(
        listOfStringToJson(this.eventTags),
        this.startTime.toString(),
        this.endTime.toString(),
        listOfIntToJson(this.groupsForEvent),
        this.id
    )

}

fun listOfIntToJson(value: List<Int>): String = Gson().toJson(value)
fun listOfStringToJson(value: List<String>): String = Gson().toJson(value)
fun jsonToList(value: String): List<String> =
    Gson().fromJson(value, Array<String>::class.java).toList()