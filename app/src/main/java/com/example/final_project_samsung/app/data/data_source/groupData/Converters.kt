package com.example.final_project_samsung.app.data.data_source.groupData

import com.example.final_project_samsung.app.data.data_source.jsonToList
import com.example.final_project_samsung.app.data.data_source.listOfStringToJson
import com.example.final_project_samsung.app.domain.model.Group


fun EntityGroup.toGroup(): Group {
    return Group(
        groupName = this.groupName,
        groupTags = jsonToList(this.groupTags),
        positionInView = this.positionInView,
        id = this.groupId
    )
}

fun Group.toEntity(): EntityGroup {
    return EntityGroup(
        groupName = this.groupName,
        groupTags = listOfStringToJson(this.groupTags),
        positionInView = this.positionInView,
        groupId = this.id
    )
}

