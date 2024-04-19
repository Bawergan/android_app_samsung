package com.example.final_project_samsung.app.presentation.addEditScreen.forGroup

sealed class AddEditGroupEvent {
    data object Save : AddEditGroupEvent()
    data object Delete : AddEditGroupEvent()
    class ChangeName(val value: String) : AddEditGroupEvent()
    class ChangeGroupTags(val value: List<String>) : AddEditGroupEvent()
}