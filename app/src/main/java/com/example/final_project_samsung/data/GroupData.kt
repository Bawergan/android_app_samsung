package com.example.final_project_samsung.data

data class GroupData(
    val id: Int,
    val eventsInGroup: MutableList<EventData> = mutableListOf(),
    val groupTags: MutableList<String> = mutableListOf(""),
    val positionInView: Int = id - 1
)

val listOfGroupData = listOf(
    GroupData(
        1,
        mutableListOf(listOfEventData[0], listOfEventData[1], listOfEventData[2]),
        mutableListOf("group 1")
    ), GroupData(
        2,
        mutableListOf(listOfEventData[3], listOfEventData[4], listOfEventData[5]),
        mutableListOf("group 2")
    ), GroupData(
        3, mutableListOf(listOfEventData[6]), mutableListOf("group 3")
    ), GroupData(
        4,
        mutableListOf(listOfEventData[0], listOfEventData[1], listOfEventData[2]),
        mutableListOf("group 4")
    ), GroupData(
        5,
        mutableListOf(listOfEventData[3], listOfEventData[4], listOfEventData[5]),
        mutableListOf("group 5")
    ), GroupData(
        6, mutableListOf(listOfEventData[6]), mutableListOf("group 6")
    )
)