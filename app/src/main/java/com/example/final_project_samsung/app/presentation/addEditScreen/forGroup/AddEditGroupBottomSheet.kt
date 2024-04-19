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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.final_project_samsung.R
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditGroupBottomSheet(
    addEditGroupViewModel: AddEditGroupViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val groupNoNameName = stringResource(R.string.no_name_group_default)
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

        if (addEditGroupViewModel.groupName.value == groupNoNameName) {
            addEditGroupViewModel.onEvent(AddEditGroupEvent.ChangeName(""))
        }

        Column {
            Button(onClick = {
                if (addEditGroupViewModel.groupName.value == "") {
                    addEditGroupViewModel.onEvent(AddEditGroupEvent.ChangeName(groupNoNameName))
                }
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