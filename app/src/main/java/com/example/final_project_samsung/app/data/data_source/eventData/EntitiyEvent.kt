package com.example.final_project_samsung.app.data.data_source.eventData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EntityEvent(
    val eventName: String,
    val eventTags: String,
    val startTime: String,
    val endTime: String,
    val groupId: Int,
    @PrimaryKey
    val eventId: Int? = null
)