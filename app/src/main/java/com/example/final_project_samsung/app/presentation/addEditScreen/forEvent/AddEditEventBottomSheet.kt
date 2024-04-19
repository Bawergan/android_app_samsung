package com.example.final_project_samsung.app.presentation.addEditScreen.forEvent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.final_project_samsung.R
import com.example.final_project_samsung.utils.CustomDatePickerDialog
import com.example.final_project_samsung.utils.CustomTimePickerDialog
import com.example.final_project_samsung.utils.add
import com.example.final_project_samsung.utils.timeFormatter
import kotlinx.coroutines.flow.collectLatest
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditEventBottomSheet(
    addEdiEventViewModel: AddEdiEventViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val eventNoNameName = stringResource(R.string.no_name_event_default)
    val isClosingState = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        addEdiEventViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEdiEventViewModel.UiEvent.SaveEvent -> {
                    navController.navigateUp()
                    isClosingState.value = true
                }

                is AddEdiEventViewModel.UiEvent.DeleteEvent -> {
                    navController.navigateUp()
                    isClosingState.value = true
                }
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            navController.navigateUp()
        },
        Modifier.navigationBarsPadding()
    ) {


        if (addEdiEventViewModel.eventName.value == eventNoNameName) {
            addEdiEventViewModel.onEvent(AddEditEventEvent.ChangeEventName(""))
        }


        Column {
            Button(onClick = {
                if (addEdiEventViewModel.eventName.value == "") {
                    addEdiEventViewModel.onEvent(AddEditEventEvent.ChangeEventName(eventNoNameName))
                }
//                if (groupsUiState.groupList.isEmpty()) {
//                    val newGroup = groupsViewModel.getNewGroupData(groupNoNameName)
//                    groupsViewModel.addGroup(newGroup)
//                }
                if (!isClosingState.value) {
                    addEdiEventViewModel.onEvent(AddEditEventEvent.SaveEvent)
                }

            }) { Text(text = "Save") }


            TextField(
                value = addEdiEventViewModel.eventName.value,
                onValueChange = {
                    addEdiEventViewModel.onEvent(
                        AddEditEventEvent.ChangeEventName(
                            it
                        )
                    )
                },
                placeholder = {
                    Text(text = "Add title")
                },
                modifier = Modifier.fillMaxWidth()
            )

            //Start Time Picker Logic
            val showStartTimePickerDialog = remember { mutableStateOf(false) }
            if (showStartTimePickerDialog.value) {
                CustomTimePickerDialog(
                    pickerStartTime = addEdiEventViewModel.eventStartTime.value.toLocalTime(),
                    onConfirm = {
                        showStartTimePickerDialog.value = false
                        addEdiEventViewModel.onEvent(
                            AddEditEventEvent.ChangeStartTime(
                                addEdiEventViewModel.eventStartTime.value.toLocalDate().add(it)
                            )
                        )
                    },
                    onDismissRequest = { showStartTimePickerDialog.value = false })
            }
            //Start Date Picker Logic
            val showStartDatePickerDialog = remember { mutableStateOf(false) }
            if (showStartDatePickerDialog.value) {
                CustomDatePickerDialog(onConfirm = {
                    showStartDatePickerDialog.value = false
                    if (it != null) {
                        addEdiEventViewModel.onEvent(
                            AddEditEventEvent.ChangeStartTime(
                                Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
                                    .add(addEdiEventViewModel.eventStartTime.value.toLocalTime())
                            )
                        )
                    }
                }, onDismissRequest = {
                    showStartDatePickerDialog.value = false //close dialog
                })
            }

            //End Time Picker Logic
            val showEndTimePickerDialog = remember { mutableStateOf(false) }
            if (showEndTimePickerDialog.value) {
                CustomTimePickerDialog(
                    pickerStartTime = addEdiEventViewModel.eventEndTime.value.toLocalTime(),
                    onConfirm = {
                        showEndTimePickerDialog.value = false
                        addEdiEventViewModel.onEvent(
                            AddEditEventEvent.ChangeEndTime(
                                addEdiEventViewModel.eventEndTime.value.toLocalDate().add(it)
                            )
                        )
                    },
                    onDismissRequest = { showEndTimePickerDialog.value = false })
            }
            //End Date Picker Logic
            val showEndDatePickerDialog = remember { mutableStateOf(false) }
            if (showEndDatePickerDialog.value) {
                CustomDatePickerDialog(onConfirm = {
                    showEndDatePickerDialog.value = false
                    if (it != null) {
                        addEdiEventViewModel.onEvent(
                            AddEditEventEvent.ChangeEndTime(
                                Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
                                    .add(addEdiEventViewModel.eventEndTime.value.toLocalTime())
                            )
                        )
                    }
                }, onDismissRequest = {
                    showEndDatePickerDialog.value = false
                })
            }

            Row {
                Text(
                    text = "${addEdiEventViewModel.eventStartTime.value.month}, ${addEdiEventViewModel.eventStartTime.value.dayOfMonth}",
                    Modifier.clickable { showStartDatePickerDialog.value = true })
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = addEdiEventViewModel.eventStartTime.value.format(timeFormatter),
                    Modifier.clickable { showStartTimePickerDialog.value = true })
            }
            Row {
                Text(
                    text = "${addEdiEventViewModel.eventEndTime.value.month}, ${addEdiEventViewModel.eventEndTime.value.dayOfMonth}",
                    Modifier.clickable { showEndDatePickerDialog.value = true })
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = addEdiEventViewModel.eventEndTime.value.format(timeFormatter),
                    Modifier.clickable { showEndTimePickerDialog.value = true })
            }

            if (addEdiEventViewModel.currentEventId.value != null) {
                Button(onClick = {
                    if (!isClosingState.value) {
                        addEdiEventViewModel.onEvent(AddEditEventEvent.DeleteEvent)
                    }
                }) {
                    Text(text = "Delete")
                }
            }
        }
    }
}