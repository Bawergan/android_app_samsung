package com.example.final_project_samsung.app.presentation.groups.customLazyLayout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.final_project_samsung.app.presentation.appNavigation.TheAppDestinations
import com.example.final_project_samsung.app.presentation.groups.EventCard
import com.example.final_project_samsung.app.presentation.groups.State
import com.example.final_project_samsung.app.presentation.groups.customLazyLayout.layout.CustomLazyLayout
import com.example.final_project_samsung.app.presentation.groups.customLazyLayout.layout.rememberLazyLayoutState

@Composable
fun CustomLazyLayoutScreen(state: State, navController: NavController) {
    val lazyLayoutState = rememberLazyLayoutState()
    CustomLazyLayout(
        state = lazyLayoutState,
        modifier = Modifier.fillMaxSize(),
    ) {
        items(state.items) { item ->
            when (item.itemType) {
                ItemType.Event -> {
                    Card(onClick = {
                        navController.navigate(
                            TheAppDestinations.ADD_EDIT_EVENT_ROUTE +
                                    "?eventId=${item.dataArray?.get(DataType.Id)?.toInt()}"
                        )
                    }) {
                        item.dataArray?.let { EventCard(it) }
                    }
                }

                ItemType.Line -> TODO()
            }
        }
    }
}