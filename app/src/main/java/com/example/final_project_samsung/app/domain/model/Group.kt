package com.example.final_project_samsung.app.domain.model

data class Group(
    val groupName: String,
    val groupTags: List<String>,
    val id: Int?,
    var positionInView: Int? = null
)