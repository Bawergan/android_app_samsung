package com.example.final_project_samsung.app.data.data_source.eventData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class EntityEvent(
    val eventName: String,
    val eventTags: String,
    val startTime: String,
    val endTime: String,
    val groupsIds: String,
    @PrimaryKey
    val id: Int? = null
)