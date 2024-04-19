package com.example.final_project_samsung.app.domain.repository

import com.example.final_project_samsung.app.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    fun getGroups(): Flow<List<Group>>
    suspend fun getGroupById(id: Int): Group?
    suspend fun insertGroup(group: Group)
    suspend fun deleteGroup(group: Group)
}