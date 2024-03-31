package com.example.final_project_samsung.ui

import java.util.Calendar
import java.util.Date

class EventData(val id: Int) {
    var tagsEvent: MutableList<String> = listOf("").toMutableList()
    var startTime: Date = Calendar.getInstance().time
    var endTime: Date = Calendar.getInstance().time
    //TODO colors
    fun start(){
        tagsEvent.add("started")
    }
    fun end(){
        tagsEvent.add("ended")
    }
}