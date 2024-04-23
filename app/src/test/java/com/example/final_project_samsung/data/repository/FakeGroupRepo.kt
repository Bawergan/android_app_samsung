package com.example.final_project_samsung.data.repository

import com.example.final_project_samsung.app.domain.model.Group
import com.example.final_project_samsung.app.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGroupRepo : GroupRepository {
    private val groups = mutableListOf<Group>()
    override fun getGroups(): Flow<List<Group>> {
        return flow { emit(groups) }
    }

    override suspend fun getGroupById(id: Int): Group? {
        return groups.find { it.id == id }
    }

    override suspend fun insertGroup(group: Group) {
        groups.add(group)
    }

    override suspend fun deleteGroup(group: Group) {
        groups.remove(group)
    }
}