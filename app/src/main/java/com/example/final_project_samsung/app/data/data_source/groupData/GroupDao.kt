package com.example.final_project_samsung.app.data.data_source.groupData

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {
    @Query("SELECT * FROM groups")
    fun getGroups(): Flow<List<EntityGroup>>

    @Query("SELECT * FROM groups WHERE groupId = :id")
    suspend fun getGroupById(id: Int): EntityGroup?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: EntityGroup)

    @Delete
    suspend fun deleteGroup(group: EntityGroup)
}