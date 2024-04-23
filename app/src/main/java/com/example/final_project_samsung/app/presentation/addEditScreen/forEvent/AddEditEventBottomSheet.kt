package com.example.final_project_samsung.app.presentation.addEditScreen.forEvent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.final_project_samsung.utils.CustomDatePickerDialog
import com.example.final_project_samsung.utils.CustomTimePickerDialog
import com.example.final_project_samsung.utils.add
import com.example.final_project_samsung.utils.timeFormatter
import kotlinx.coroutines.flow.collectLatest
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun AddEditEventBottomSheet(
    addEdiEventViewModel: AddEdiEventViewModel = hiltViewModel(),
    navController: NavHostController
) {
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
    Scaffold {

        val showStartTimePickerDialog = remember { mutableStateOf(false) }
        val showStartDatePickerDialog = remember { mutableStateOf(false) }
        val showEndTimePickerDialog = remember { mutableStateOf(false) }
        val showEndDatePickerDialog = remember { mutableStateOf(false) }
        DateTimePickerLogic(
            addEdiEventViewModel,
            showEndDatePickerDialog,
            showEndTimePickerDialog,
            showStartDatePickerDialog,
            showStartTimePickerDialog
        )
        Column(
            modifier = Modifier
                .padding(it)
                .padding(top = TOP_ROW_HEIGHT)
//                .systemBarsPadding()
        ) {
            Spacer(Modifier.height(TOP_ROW_HEIGHT / 2))

            Box {
                Column {
                    Spacer(modifier = Modifier.height(30.dp))
                    Row {
                        Spacer(modifier = Modifier.width(RIGHT_COLUMN_WIDTH - 16.dp))
                        TextField(
                            value = addEdiEventViewModel.eventName.value,
                            onValueChange = {
                                addEdiEventViewModel.onEvent(
                                    AddEditEventEvent.ChangeName(
                                        it
                                    )
                                )
                            },
                            placeholder = {
                                Text(
                                    text = "Add title",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontSize = TextUnit(
                                            27F, TextUnitType.Sp
                                        ),
                                        color = TextFieldDefaults.colors().unfocusedPrefixColor
                                    )
                                )
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = TOP_ROW_HEIGHT, end = TOP_ROW_HEIGHT),
                            colors = myTextFieldColors(),
                            textStyle = MaterialTheme.typography.titleLarge.copy(
                                fontSize = TextUnit(
                                    27F, TextUnitType.Sp
                                )
                            )
                        )
                    }
                    HorizontalDivider()
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(
                                if (addEdiEventViewModel.eventStartTime.value > addEdiEventViewModel.eventEndTime.value) {
                                    MaterialTheme.colorScheme.onError
                                } else MaterialTheme.colorScheme.background
                            )
                            .padding(start = TOP_ROW_HEIGHT, end = TOP_ROW_HEIGHT)
                    ) {
                        Spacer(modifier = Modifier.width(RIGHT_COLUMN_WIDTH))
                        CustomText(
                            text = "${addEdiEventViewModel.eventStartTime.value.month}, ${addEdiEventViewModel.eventStartTime.value.dayOfMonth}",
                            Modifier
                                .clickable { showStartDatePickerDialog.value = true }
                                .width(250.dp)
                        )
                        CustomText(
                            text = addEdiEventViewModel.eventStartTime.value.format(timeFormatter),
                            Modifier
                                .clickable { showStartTimePickerDialog.value = true }
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        )
                    }
                    Row(Modifier.padding(start = TOP_ROW_HEIGHT, end = TOP_ROW_HEIGHT)) {
                        Spacer(modifier = Modifier.width(RIGHT_COLUMN_WIDTH))
                        CustomText(
                            text = "${addEdiEventViewModel.eventEndTime.value.month}, ${addEdiEventViewModel.eventEndTime.value.dayOfMonth}",
                            Modifier
                                .clickable { showEndDatePickerDialog.value = true }
                                .width(250.dp)
                        )
                        CustomText(
                            text = addEdiEventViewModel.eventEndTime.value.format(timeFormatter),
                            Modifier
                                .clickable { showEndTimePickerDialog.value = true }
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        )
                    }
                    if (addEdiEventViewModel.currentEventId.value != null) {

                        Row(
                            Modifier.padding(start = TOP_ROW_HEIGHT, end = TOP_ROW_HEIGHT),
                        ) {
                            Spacer(modifier = Modifier.width(RIGHT_COLUMN_WIDTH))
                            CustomButton(onClick = {
                                if (!isClosingState.value) {
                                    addEdiEventViewModel.onEvent(AddEditEventEvent.Delete)
                                }
                            }, colors = myButtonColors()) {
                                Icon(Icons.Filled.Delete, contentDescription = "Delete event")
                            }
                        }
                    }

                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(start = TOP_ROW_HEIGHT, end = TOP_ROW_HEIGHT),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    CustomButton(
                        onClick = {
//                if (groupsUiState.groupList.isEmpty()) {
//                    val newGroup = groupsViewModel.getNewGroupData(groupNoNameName)
//                    groupsViewModel.addGroup(newGroup)
//                }
                            if (!isClosingState.value) {
                                addEdiEventViewModel.onEvent(AddEditEventEvent.Save)
                            }

                        },
                        modifier = Modifier
                            .size(54.dp, 32.dp)
                    ) { Text(text = "Save") }
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(start = TOP_ROW_HEIGHT, end = TOP_ROW_HEIGHT),
                    contentAlignment = Alignment.CenterStart
                ) {
                    CustomButton(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background),
                        onClick = {
                            navController.navigateUp()
                        }, colors = myButtonColors()
                    ) { Icon(Icons.Filled.Close, contentDescription = "Close edit screen") }
                }
            }
        }
    }
}

@Composable
@Preview
fun Preview() {
    Scaffold(
        Modifier.navigationBarsPadding()
    ) {
        val time = LocalDateTime.now()
        Column(
            Modifier
                .padding(it)
                .padding(TOP_ROW_HEIGHT)
                .systemBarsPadding()
        ) {
            Spacer(Modifier.height(TOP_ROW_HEIGHT / 2))
            Box {
                Column {
                    Spacer(modifier = Modifier.height(30.dp))
                    Row {
                        Spacer(modifier = Modifier.width(RIGHT_COLUMN_WIDTH - 16.dp))
                        TextField(
                            value = "theName",
                            onValueChange = {
                            },
                            placeholder = {
                                Text(text = "Add title")
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = myTextFieldColors(),
                            textStyle = MaterialTheme.typography.titleLarge.copy(
                                fontSize = TextUnit(
                                    27F, TextUnitType.Sp
                                )
                            )
                        )
                    }
                    HorizontalDivider()
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(
                                if (true) {
                                    MaterialTheme.colorScheme.onError
                                } else MaterialTheme.colorScheme.background
                            )
                    ) {
                        Spacer(modifier = Modifier.width(RIGHT_COLUMN_WIDTH))
                        CustomText(
                            text = "${time.month}, ${time.dayOfMonth}",
                            Modifier
                                .width(250.dp),
                        )
                        CustomText(
                            text = time.format(timeFormatter),
                            Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        )
                    }
                    Row {
                        Spacer(modifier = Modifier.width(RIGHT_COLUMN_WIDTH))
                        CustomText(
                            text = "${time.month}, ${time.dayOfMonth}",
                            Modifier
                                .width(250.dp)
                        )
                        CustomText(
                            text = time.format(timeFormatter),
                            Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        )
                    }
                    Row {
                        Spacer(modifier = Modifier.width(RIGHT_COLUMN_WIDTH))
                        CustomButton(onClick = {
                        }, colors = myButtonColors()) {
                            Icon(Icons.Filled.Delete, contentDescription = "Delete event")
                        }
                    }
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(40.dp), contentAlignment = Alignment.CenterEnd
                ) {
                    CustomButton(onClick = {
                    }, modifier = Modifier.size(55.dp, 35.dp)) { Text(text = "Save") }
                }
            }
        }
    }
}

private val RIGHT_COLUMN_WIDTH = 50.dp
private val TOP_ROW_HEIGHT = 10.dp

@Composable
fun myButtonColors(): ButtonColors {
    return ButtonDefaults.buttonColors().copy(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
    )
}

@Composable
fun myTextFieldColors(): TextFieldColors {
    return TextFieldDefaults.colors()
        .copy(
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background

        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerLogic(
    addEdiEventViewModel: AddEdiEventViewModel,
    showEndDatePickerDialog: MutableState<Boolean>,
    showEndTimePickerDialog: MutableState<Boolean>,
    showStartDatePickerDialog: MutableState<Boolean>,
    showStartTimePickerDialog: MutableState<Boolean>
) {
    //Start Time Picker Logic
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
}

@Composable
fun CustomText(
    text: String,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.CenterStart
) {
    val typography = MaterialTheme.typography.bodyLarge
    Box(modifier = modifier.height(40.dp), contentAlignment = contentAlignment) {
        Text(
            text = text,
            style = typography,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}

@Composable
fun CustomButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
//    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val containerColor =
        if (enabled) colors.containerColor else colors.disabledContainerColor
    val contentColor =
        if (enabled) colors.contentColor else colors.disabledContentColor
    val shadowElevation = 0.dp
    val tonalElevation = 0.dp
    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        enabled = enabled,
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        border = border,
        interactionSource = interactionSource
    ) {
        val textStyle = MaterialTheme.typography.labelLarge
        val mergedStyle = LocalTextStyle.current.merge(textStyle)
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            LocalTextStyle provides mergedStyle,
            content = {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = ButtonDefaults.MinHeight,
                            minHeight = ButtonDefaults.MinHeight
                        )
                        .padding(0.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        )
    }
}

