package com.example.final_project_samsung.app.presentation.groups.customLazyLayout.layout

import androidx.compose.runtime.Composable
import com.example.final_project_samsung.app.presentation.groups.customLazyLayout.ListItem

typealias ComposableItemContent = @Composable (ListItem) -> Unit

data class LazyLayoutItemContent(
    val item: ListItem,
    val itemContent: ComposableItemContent
)
