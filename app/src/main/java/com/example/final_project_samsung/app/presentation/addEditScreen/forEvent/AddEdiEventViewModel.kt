package com.example.final_project_samsung.app.presentation.addEditScreen.forEvent

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project_samsung.app.domain.model.Event
import com.example.final_project_samsung.app.domain.use_case.forEvent.EventUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddEdiEventViewModel @Inject constructor(
    private val eventUseCases: EventUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _eventName = mutableStateOf("")
    val eventName: State<String> = _eventName

    private val _eventStartTime = mutableStateOf(LocalDateTime.now())
    val eventStartTime: State<LocalDateTime> = _eventStartTime

    private val _eventEndTime = mutableStateOf(LocalDateTime.now().plusHours(1))
    val eventEndTime: State<LocalDateTime> = _eventEndTime

    private val _groupId = mutableIntStateOf(1)
    val groupId: State<Int> = _groupId

    private val _eventTags = mutableStateListOf<String>()
    val eventTags: SnapshotStateList<String> = _eventTags

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var _currentEventId: MutableState<Int?> = mutableStateOf(null)
    val currentEventId: State<Int?> = _currentEventId

    init {
        savedStateHandle.get<Int>("eventId").let { eventId ->
            if (eventId != -1 && eventId != null) {
                viewModelScope.launch {
                    eventUseCases.getEventById(eventId)?.also { event ->
                        _currentEventId.value = event.id
                        _eventName.value = event.eventName
                        _eventStartTime.value = event.startTime
                        _eventEndTime.value = event.endTime
                        _groupId.value = event.groupId
                        _eventTags.addAll(event.eventTags)
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditEventEvent) {
        when (event) {
            is AddEditEventEvent.Save -> {
                viewModelScope.launch {
                    eventUseCases.addEvent(
                        Event(
                            eventName = eventName.value,
                            eventTags = eventTags.toMutableList(),
                            groupId = groupId.value,
                            startTime = eventStartTime.value,
                            endTime = eventEndTime.value,
                            id = _currentEventId.value
                        )
                    )
                    _eventFlow.emit(UiEvent.SaveEvent)
                }
            }

            is AddEditEventEvent.Delete -> {
                viewModelScope.launch {
                    eventUseCases.deleteEvent(
                        eventUseCases.getEventById(
                            _currentEventId.value!!
                        )!!
                    )
                    _eventFlow.emit(UiEvent.DeleteEvent)
                }
            }

            is AddEditEventEvent.ChangeName -> {
                _eventName.value = event.value
            }

            is AddEditEventEvent.ChangeStartTime -> {
                _eventStartTime.value = event.value
            }

            is AddEditEventEvent.ChangeEndTime -> {
                _eventEndTime.value = event.value
            }

            is AddEditEventEvent.ChangeGroup -> {
                _groupId.intValue = event.value
            }
        }
    }

    sealed class UiEvent {
        data object SaveEvent : UiEvent()
        data object DeleteEvent : UiEvent()
    }
}