package com.example.final_project_samsung.app.data.data_source.groupData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class EntityGroup(
    val groupName: String,
    val groupTags: String,
    val positionInView: Int?,
    @PrimaryKey
    val groupId: Int? = null
)