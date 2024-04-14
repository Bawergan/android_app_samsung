package com.example.final_project_samsung.ui.groups

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.final_project_samsung.R
import com.example.final_project_samsung.data.listOfEventData
import com.example.final_project_samsung.data.listOfGroupData
import com.example.final_project_samsung.utils.CustomDatePickerDialog
import com.example.final_project_samsung.utils.CustomTimePickerDialog
import com.example.final_project_samsung.utils.add
import com.example.final_project_samsung.utils.timeFormatter
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditingBottomSheetManager(
    groupsUiState: GroupsUiState,
    groupsViewModel: GroupsViewModel,
) {

    val groupNoNameName = stringResource(R.string.no_name_group_default)
    val eventNoNameName = stringResource(R.string.no_name_event_default)

    if (groupsUiState.isGroupEditingBottomSheetOpen.value) {
        ModalBottomSheet(onDismissRequest = {
            groupsUiState.isGroupEditingBottomSheetOpen.value = false
            groupsUiState.chosenGroup.value = null
        }) {

            val group = if (groupsUiState.chosenGroup.value != null) {
                groupsUiState.groupList[groupsUiState.chosenGroup.value!!]
            } else {
                groupsViewModel.getNewGroupData(groupNoNameName)
            }

            var newName by remember { mutableStateOf("") }
            if (newName == groupNoNameName) {
                newName = ""
            }

            Column {
                Button(onClick = {
                    if (newName == "") {
                        newName = groupNoNameName
                    }

                    group.groupTags[0] = newName

                    groupsUiState.groupList.add(group)
                    listOfGroupData.add(group)

                    groupsUiState.chosenGroup.value = null
                    groupsUiState.isGroupEditingBottomSheetOpen.value = false
                    //skill issue
//                    groupsUiState.groupList.add(GroupData(999999))
//                    groupsUiState.groupList.removeLast()

                }) { Text(text = "Save") }

                TextField(value = newName, onValueChange = { newName = it }, placeholder = {
                    Text(text = "Add title")
                }, modifier = Modifier.fillMaxWidth()
                )

                if (groupsUiState.chosenGroup.value != null) {
                    Button(onClick = {
                        groupsUiState.groupList.remove(group)
                        listOfGroupData.remove(group)

                        groupsUiState.chosenGroup.value = null
                        groupsUiState.isGroupEditingBottomSheetOpen.value = false
                    }) {
                        Text(text = "Delete")
                    }
                }
            }
        }
    }

    if (groupsUiState.isEventEditingBottomSheetOpen.value) {
        ModalBottomSheet(onDismissRequest = {
            groupsUiState.isEventEditingBottomSheetOpen.value = false
            groupsUiState.chosenEvent.value = null
        }, Modifier.navigationBarsPadding()) {

            val event = if (groupsUiState.chosenEvent.value != null) {
                groupsUiState.eventList[groupsUiState.chosenEvent.value!!]
            } else {
                groupsViewModel.getNewEventData(eventNoNameName)
            }

            var newName by remember { mutableStateOf("") }
            if (newName == eventNoNameName) {
                newName = ""
            }

            val newStartTime = remember { mutableStateOf(event.startTime.toLocalTime()) }
            val newStartDate = remember { mutableStateOf(event.startTime.toLocalDate()) }

            val newEndTime = remember { mutableStateOf(event.endTime.toLocalTime()) }
            val newEndDate = remember { mutableStateOf(event.endTime.toLocalDate()) }

            Column {
                Button(onClick = {
                    if (newName == "") {
                        newName = eventNoNameName
                    }
                    if (groupsUiState.groupList.size == 0) {
                        val newGroup = groupsViewModel.getNewGroupData(groupNoNameName)
                        groupsUiState.groupList.add(newGroup)
                        listOfGroupData.add(newGroup)
                    }
                    event.eventTags[0] = newName
                    event.startTime = newStartDate.value.add(newStartTime.value)
                    event.endTime = newEndDate.value.add(newEndTime.value)

                    groupsUiState.chosenEvent.value = null
                    groupsUiState.isEventEditingBottomSheetOpen.value = false

                    listOfEventData.add(event)
                    groupsUiState.eventList.add(event)
                }) { Text(text = "Save") }

                TextField(value = newName, onValueChange = { newName = it }, placeholder = {
                    Text(text = "Add title")
                }, modifier = Modifier.fillMaxWidth()
                )

                val showStartTimePickerDialog = remember { mutableStateOf(false) }
                val showStartDatePickerDialog = remember { mutableStateOf(false) }

                if (showStartTimePickerDialog.value) {
                    CustomTimePickerDialog(onAccept = {
                        showStartTimePickerDialog.value = false
                        newStartTime.value = it
                    }, onCancel = { showStartTimePickerDialog.value = false })
                }

                if (showStartDatePickerDialog.value) {
                    CustomDatePickerDialog(onAccept = {
                        showStartDatePickerDialog.value = false
                        if (it != null) { // Set the date
                            newStartDate.value =
                                Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
                        }
                    }, onCancel = {
                        showStartDatePickerDialog.value = false //close dialog
                    })
                }

                Row {
                    Text(
                        text = "${newStartDate.value.month}, ${newStartDate.value.dayOfMonth}",
                        Modifier.clickable { showStartDatePickerDialog.value = true })
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = newStartTime.value.format(timeFormatter),
                        Modifier.clickable { showStartTimePickerDialog.value = true })
                }

                val showEndTimePickerDialog = remember { mutableStateOf(false) }
                val showEndDatePickerDialog = remember { mutableStateOf(false) }

                if (showEndTimePickerDialog.value) {
                    CustomTimePickerDialog(onAccept = {
                        showEndTimePickerDialog.value = false
                        newEndTime.value = it
                    }, onCancel = { showEndTimePickerDialog.value = false })
                }

                if (showEndDatePickerDialog.value) {
                    CustomDatePickerDialog(onAccept = {
                        showEndDatePickerDialog.value = false
                        if (it != null) { // Set the date
                            newEndDate.value =
                                Instant.ofEpochMilli(it).atZone(ZoneId.of("UTC")).toLocalDate()
                        }
                    }, onCancel = {
                        showEndDatePickerDialog.value = false //close dialog
                    })
                }

                Row {
                    Text(
                        text = "${newEndDate.value.month}, ${newEndDate.value.dayOfMonth}",
                        Modifier.clickable { showEndDatePickerDialog.value = true })
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = newEndTime.value.format(timeFormatter),
                        Modifier.clickable { showEndTimePickerDialog.value = true })
                }

                if (groupsUiState.chosenEvent.value != null) {
                    Button(onClick = {
                        groupsUiState.chosenEvent.value = null
                        groupsUiState.isEventEditingBottomSheetOpen.value = false

                        listOfEventData.remove(event)
                        groupsUiState.eventList.remove(event)
                    }) {
                        Text(text = "Delete")
                    }
                }
            }
        }
    }
}