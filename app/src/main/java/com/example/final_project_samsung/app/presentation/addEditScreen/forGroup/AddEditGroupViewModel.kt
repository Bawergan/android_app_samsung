package com.example.final_project_samsung.app.presentation.addEditScreen.forGroup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project_samsung.app.domain.model.Group
import com.example.final_project_samsung.app.domain.use_case.forGroup.GroupUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditGroupViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val groupUseCases: GroupUseCases
) : ViewModel() {
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _groupName = mutableStateOf("")
    val groupName: State<String> = _groupName

    private val _groupTags = mutableStateListOf<String>()
    val groupTags: SnapshotStateList<String> = _groupTags

    private val _groupId = mutableStateOf<Int?>(null)
    val groupId: State<Int?> = _groupId

    private val _positionInView = mutableStateOf<Int?>(null)
    val positionInView: State<Int?> = _positionInView

    init {
        savedStateHandle.get<Int?>("groupId").let { groupId ->
            if (groupId != -1 && groupId != null) {
                viewModelScope.launch {
                    groupUseCases.getGroupById(groupId)?.also { group ->
                        _groupTags.addAll(group.groupTags)
                        _groupId.value = group.id
                        _positionInView.value = group.positionInView
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditGroupEvent) {
        when (event) {
            AddEditGroupEvent.Save -> {
                viewModelScope.launch {
                    groupUseCases.addGroup(
                        Group(
                            groupName = _groupName.value,
                            groupTags = _groupTags.toList(),
                            id = _groupId.value,
                            positionInView = _positionInView.value,
                        )
                    )
                    _eventFlow.emit(UiEvent.Save)
                }
            }

            AddEditGroupEvent.Delete -> {
                viewModelScope.launch {
                    groupUseCases.deleteGroup(groupUseCases.getGroupById(_groupId.value!!)!!)
                    _eventFlow.emit(UiEvent.Delete)
                }
            }

            is AddEditGroupEvent.ChangeGroupTags -> {
                _groupTags.clear()
                _groupTags.addAll(event.value)
            }

            is AddEditGroupEvent.ChangeName -> {
                _groupName.value = event.value
            }
        }
    }

    sealed class UiEvent {
        data object Save : UiEvent()
        data object Delete : UiEvent()
    }
}
