package com.example.final_project_samsung.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerDialog(
    pickerStartTime: LocalTime? = null,
    onDismissRequest: () -> Unit,
    onConfirm: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = DatePickerDefaults.shape,
    tonalElevation: Dp = DatePickerDefaults.TonalElevation,
    colors: TimePickerColors = TimePickerDefaults.colors(),
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false)
) {
    val state = rememberTimePickerState(
        initialMinute = pickerStartTime?.minute ?: 0,
        initialHour = pickerStartTime?.hour ?: 12
    )
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier.wrapContentHeight(),
        properties = properties,
    ) {
        Surface(
            modifier = Modifier
                .heightIn(max = 568.0.dp),
            shape = shape,
            color = colors.containerColor,
            tonalElevation = tonalElevation,
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(state = state)
                // Buttons
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(bottom = 8.dp, end = 6.dp)
                ) {
                    Text(text = "Cancel",
                        Modifier
                            .clickable { onDismissRequest() }
                            .padding(end = 10.dp), color = colors.timeSelectorSelectedContentColor)
                    Text(
                        "Accept",
                        Modifier.clickable { onConfirm(state.toLocalTime()) },
                        color = colors.timeSelectorSelectedContentColor
                    )
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
    onConfirm: (Long?) -> Unit,
    onDismissRequest: () -> Unit
) {
    val state = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            Text("Accept", Modifier.clickable { onConfirm(state.selectedDateMillis) })
        },
        dismissButton = {
            Text("Cancel", Modifier.clickable { onDismissRequest() })
        }
    ) {
        DatePicker(state = state)
    }
}

fun LocalDate.add(time: LocalTime): LocalDateTime {
    return LocalDateTime.of(this, time)
}