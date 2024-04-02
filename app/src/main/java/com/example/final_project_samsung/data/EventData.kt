package com.example.final_project_samsung.data

import java.util.Calendar
import java.util.Date

class EventData(val id: Int) {
    val eventTags = mutableListOf("(No title)")
    val eventGroups: MutableList<String> = mutableListOf()
    var startTime: Date = Calendar.getInstance().time
    var endTime: Date = Calendar.getInstance().time

    //TODO colors
    fun onEventStart() {
    }

    fun onEventEnd() {
        endTime = Calendar.getInstance().time
        eventTags.add("ended")
    }

    fun addEventToGroup(group: String) {
        eventGroups.add(group)
    }
}