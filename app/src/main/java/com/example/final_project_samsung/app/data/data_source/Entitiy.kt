package com.example.final_project_samsung.app.data.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class EntityEvent(
    val eventTags: String,
    val startTime: String,
    val endTime: String,
    val groupsIds: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null
)

