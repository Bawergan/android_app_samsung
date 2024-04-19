package com.example.final_project_samsung.app.domain.use_case.forGroup

import com.example.final_project_samsung.app.domain.model.Group
import com.example.final_project_samsung.app.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow

class GetGroups(
    private val repository: GroupRepository
) {
    operator fun invoke(): Flow<List<Group>> {
        return repository.getGroups()
    }
}