package com.example.final_project_samsung.app.presentation.groups.customLazyLayout.layout

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import com.example.final_project_samsung.app.presentation.groups.customLazyLayout.ListItem

interface CustomLazyListScope {
    fun item(item: ListItem, itemContent: ComposableItemContent)

    fun items(items: List<ListItem>, itemContent: ComposableItemContent)
    fun stickyHeader(
        key: Any? = null,
        contentType: Any? = null,
        content: @Composable LazyItemScope.() -> Unit
    )
}

class CustomLazyListScopeImpl : CustomLazyListScope {

    private val _items = mutableListOf<LazyLayoutItemContent>()
    val items: List<LazyLayoutItemContent> = _items
    override fun item(item: ListItem, itemContent: ComposableItemContent) {
        _items.add(LazyLayoutItemContent(item, itemContent))
    }

    override fun items(items: List<ListItem>, itemContent: ComposableItemContent) {
        items.forEach { _items.add(LazyLayoutItemContent(it, itemContent)) }
    }

    override fun stickyHeader(
        key: Any?,
        contentType: Any?,
        content: @Composable LazyItemScope.() -> Unit
    ) {
        //TODO
    }
}
