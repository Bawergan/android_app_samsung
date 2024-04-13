package com.example.final_project_samsung.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerDialog(
    onAccept: (LocalTime) -> Unit,
    onCancel: () -> Unit
) {
    val state = rememberTimePickerState()
    BasicAlertDialog(
        onDismissRequest = { onCancel() },
        modifier = Modifier.wrapContentHeight(),
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            TimePicker(state = state)
            // Buttons
            Box(
                modifier = Modifier
                    .align(Alignment.End)
            ) {

                Button(onClick = onCancel) {
                    Text("Cancel")
                }
                Button(onClick = { onAccept(state.toLocalTime()) }) {
                    Text("Accept")

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun TimePickerState.toLocalTime(): LocalTime {
    val formatter = DateTimeFormatter.ofPattern("HH-mm")
    var chars = ""
    if (this.hour < 10) {
        chars += "0"
    }
    chars += "${this.hour}-"
    if (this.minute < 10) {
        chars += "0"
    }
    chars += "${this.minute}"
    return LocalTime.parse(chars, formatter)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onAccept: (Long?) -> Unit,
    onCancel: () -> Unit
) {
    val state = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = { onCancel() },
        confirmButton = {
            Button(onClick = { onAccept(state.selectedDateMillis) }) {
                Text("Accept")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = state)
    }
}

fun LocalDate.add(time: LocalTime): LocalDateTime {
    return LocalDateTime.of(this, time)
}