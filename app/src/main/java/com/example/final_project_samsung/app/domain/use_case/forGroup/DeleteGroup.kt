package com.example.final_project_samsung.app.domain.use_case.forGroup

import com.example.final_project_samsung.app.domain.model.Group
import com.example.final_project_samsung.app.domain.repository.GroupRepository

class DeleteGroup(
    private val repository: GroupRepository
) {
    suspend operator fun invoke(group: Group) {
        repository.deleteGroup(group)
    }
}