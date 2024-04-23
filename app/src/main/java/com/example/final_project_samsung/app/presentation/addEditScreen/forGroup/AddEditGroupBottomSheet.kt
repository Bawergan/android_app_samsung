package com.example.final_project_samsung.app.presentation.addEditScreen.forGroup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.final_project_samsung.app.presentation.addEditScreen.forEvent.CustomButton
import com.example.final_project_samsung.app.presentation.addEditScreen.forEvent.myButtonColors
import com.example.final_project_samsung.app.presentation.addEditScreen.forEvent.myTextFieldColors
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditGroupBottomSheet(
    addEditGroupViewModel: AddEditGroupViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val isClosingState = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        addEditGroupViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditGroupViewModel.UiEvent.Save -> {
                    navController.navigateUp()
                    isClosingState.value = true
                }

                is AddEditGroupViewModel.UiEvent.Delete -> {
                    navController.navigateUp()
                    isClosingState.value = true
                }
            }
        }
    }
    Scaffold {
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
                            value = addEditGroupViewModel.groupName.value,
                            onValueChange = {
                                addEditGroupViewModel.onEvent(
                                    AddEditGroupEvent.ChangeName(
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
                    if (addEditGroupViewModel.groupId.value != null) {
                        Row(
                            Modifier.padding(start = TOP_ROW_HEIGHT, end = TOP_ROW_HEIGHT),
                        ) {
                            Spacer(modifier = Modifier.width(RIGHT_COLUMN_WIDTH))
                            CustomButton(onClick = {
                                if (!isClosingState.value) {
                                    addEditGroupViewModel.onEvent(AddEditGroupEvent.Delete)
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
                            if (!isClosingState.value) {
                                addEditGroupViewModel.onEvent(AddEditGroupEvent.Save)
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

private val RIGHT_COLUMN_WIDTH = 50.dp
private val TOP_ROW_HEIGHT = 10.dp