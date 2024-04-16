package com.example.final_project_samsung.app.domain.model

import com.example.final_project_samsung.theme.BabyBlue
import com.example.final_project_samsung.theme.LightGreen
import com.example.final_project_samsung.theme.RedOrange
import com.example.final_project_samsung.theme.RedPink
import com.example.final_project_samsung.theme.Violet
import java.time.LocalDateTime

data class Event(
    val eventTags: MutableList<String>,
    var startTime: LocalDateTime,
    var endTime: LocalDateTime,
    var groupsForEvent: MutableList<Int>,
    val id: Int?
) {
    companion object {
        val eventColors = listOf(RedOrange, RedPink, BabyBlue, Violet, LightGreen)
    }
}
