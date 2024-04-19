package com.example.final_project_samsung.app.presentation.addEditScreen.forGroup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
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
                    isClosingState.value = true
                    navController.navigateUp()
                }

                is AddEditGroupViewModel.UiEvent.Delete -> {
                    isClosingState.value = true
                    navController.navigateUp()
                }
            }
        }

    }

    ModalBottomSheet(
        onDismissRequest = {
            navController.navigateUp()
        }, Modifier.navigationBarsPadding()
    ) {

        Column {
            Button(onClick = {
                if (!isClosingState.value) {
                    addEditGroupViewModel.onEvent(AddEditGroupEvent.Save)
                }
            }) { Text(text = "Save") }

            TextField(
                value = addEditGroupViewModel.groupName.value,
                onValueChange = { addEditGroupViewModel.onEvent(AddEditGroupEvent.ChangeName(it)) },
                placeholder = {
                    Text(text = "Add title")
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (addEditGroupViewModel.groupId.value != null) {
                Button(onClick = {
                    if (!isClosingState.value) {
                        addEditGroupViewModel.onEvent(AddEditGroupEvent.Delete)
                    }
                }) {
                    Text(text = "Delete")
                }
            }
        }
    }

}