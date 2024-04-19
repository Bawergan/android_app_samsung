package com.example.final_project_samsung.app.domain.use_case.forGroup

data class GroupUseCases(
    val getGroups: GetGroups,
    val deleteGroup: DeleteGroup,
    val getGroupById: GetGroupById,
    val addGroup: AddGroup
)