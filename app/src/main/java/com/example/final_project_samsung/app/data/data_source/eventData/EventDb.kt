package com.example.final_project_samsung.app.data.data_source.eventData

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [EntityEvent::class],
    version = 1
)
abstract class EventDb : RoomDatabase() {
    abstract val eventDao: EventDao

    companion object {
        const val DATABASE_NAME = "event_db"
    }
}