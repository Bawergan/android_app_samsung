package com.example.final_project_samsung.app.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.final_project_samsung.app.data.data_source.eventData.EntityEvent
import com.example.final_project_samsung.app.data.data_source.eventData.EventDao
import com.example.final_project_samsung.app.data.data_source.groupData.EntityGroup
import com.example.final_project_samsung.app.data.data_source.groupData.GroupDao

@Database(
    entities = [EntityEvent::class, EntityGroup::class],
    version = 1
)
abstract class TheAppDb : RoomDatabase() {
    abstract val eventDao: EventDao
    abstract val groupDao: GroupDao

    companion object {
        const val DATABASE_NAME = "theApp_db"
    }
}