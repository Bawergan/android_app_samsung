package com.example.final_project_samsung.app.data.data_source.groupData

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [EntityGroup::class],
    version = 1
)
abstract class GroupDb : RoomDatabase() {
    abstract val groupDao: GroupDao

    companion object {
        const val DATABASE_NAME = "group_db"
    }
}