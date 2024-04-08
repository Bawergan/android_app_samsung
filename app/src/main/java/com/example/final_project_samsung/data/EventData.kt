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

val listOfEventData = listOf(
    EventData(
        1,
        mutableListOf("my first title"),
        LocalDateTime.parse("2024-04-07T15:30"),
        LocalDateTime.parse("2024-04-07T20:30")
    ), EventData(
        2,
        mutableListOf("my title"),
        LocalDateTime.parse("2024-04-07T17:30"),
        LocalDateTime.parse("2024-04-07T20:30")
    ), EventData(
        3,
        mutableListOf("my third title"),
        LocalDateTime.parse("2024-04-07T18:30"),
        LocalDateTime.parse("2024-04-07T20:30")
    ), EventData(
        4,
        mutableListOf("title"),
        LocalDateTime.parse("2024-04-07T18:30"),
        LocalDateTime.parse("2024-04-07T20:30")
    ), EventData(
        5,
        mutableListOf(""),
        LocalDateTime.parse("2024-04-07T18:30"),
        LocalDateTime.parse("2024-04-07T20:30")
    ), EventData(
        6,
        mutableListOf("This is very long title"),
        LocalDateTime.parse("2024-04-07T18:30"),
        LocalDateTime.parse("2024-04-07T20:30")
    ), EventData(
        7,
        mutableListOf("final title"),
        LocalDateTime.parse("2024-04-07T19:30"),
        LocalDateTime.parse("2024-04-07T20:30")
    ), EventData(
        8,
        mutableListOf("1"),
        LocalDateTime.parse("2024-04-07T11:30"),
        LocalDateTime.parse("2024-04-07T12:30")
    )
)