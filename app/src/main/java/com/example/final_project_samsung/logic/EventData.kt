package com.example.final_project_samsung.logic

import java.util.Calendar
import java.util.Date

class EventData(val id: Int) {
    var tagsEvent: MutableList<String> = listOf<String>("").toMutableList()
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