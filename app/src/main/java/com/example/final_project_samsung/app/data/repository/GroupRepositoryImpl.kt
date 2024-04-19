package com.example.final_project_samsung.app.data.repository

import com.example.final_project_samsung.app.data.data_source.groupData.GroupDao
import com.example.final_project_samsung.app.data.data_source.groupData.toEntity
import com.example.final_project_samsung.app.data.data_source.groupData.toGroup
import com.example.final_project_samsung.app.domain.model.Group
import com.example.final_project_samsung.app.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GroupRepositoryImpl(
    private val dao: GroupDao
) : GroupRepository {
    override fun getGroups(): Flow<List<Group>> {
        return dao.getGroups().map { it.map { result -> result.toGroup() } }

//        return flowOf(fakeRepo)
    }

    override suspend fun getGroupById(id: Int): Group? {
        return dao.getGroupById(id)?.toGroup()
    }

    override suspend fun insertGroup(group: Group) {
        dao.insertGroup(group.toEntity())
    }

    override suspend fun deleteGroup(group: Group) {
        dao.deleteGroup(group.toEntity())
    }
}