package com.example.final_project_samsung.data

import java.time.LocalDateTime

data class EventData(
    val id: Int,
    val eventTags: MutableList<String> = mutableListOf("(No title)"),
    var startTime: LocalDateTime = LocalDateTime.now(),
    var endTime: LocalDateTime = LocalDateTime.now(),
    var groupsForEvent: MutableList<GroupData> = mutableListOf()

    //TODO colors
)

val listOfEventData: List<EventData> = listOf(
    EventData(
        1,
        mutableListOf("my first title"),
        LocalDateTime.parse("2024-04-07T15:30"),
        LocalDateTime.parse("2024-04-07T20:30")
    ),
    EventData(
        2,
        mutableListOf("my first title"),
        LocalDateTime.parse("2024-04-07T15:30"),
        LocalDateTime.parse("2024-04-07T20:30")
    ),
    EventData(
        3,
        mutableListOf("my first title"),
        LocalDateTime.parse("2024-04-08T15:30"),
        LocalDateTime.parse("2024-04-08T20:30")
    ),
    EventData(
        4,
        mutableListOf("my first title"),
        LocalDateTime.parse("2024-04-07T21:30"),
        LocalDateTime.parse("2024-04-08T02:30")
    ),
)