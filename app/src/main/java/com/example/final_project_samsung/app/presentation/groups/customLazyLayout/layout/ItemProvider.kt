package com.example.final_project_samsung.app.presentation.groups.customLazyLayout.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.final_project_samsung.app.presentation.groups.customLazyLayout.ListItem

@Composable
fun rememberItemProvider(customLazyListScope: CustomLazyListScope.() -> Unit): ItemProvider {
    val customLazyListScopeState = remember { mutableStateOf(customLazyListScope) }.apply {
        value = customLazyListScope
    }

    return remember {
        ItemProvider(
            derivedStateOf {
                val layoutScope = CustomLazyListScopeImpl().apply(customLazyListScopeState.value)
                layoutScope.items
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
class ItemProvider(
    private val itemsState: State<List<LazyLayoutItemContent>>
) : LazyLayoutItemProvider {

    override val itemCount
        get() = itemsState.value.size

    @Composable
    override fun Item(index: Int, key: Any) {
        val item = itemsState.value.getOrNull(index)
        item?.itemContent?.invoke(item.item)
    }

    fun getItemIndexesInRange(boundaries: ViewBoundaries): List<Int> {
        val result = mutableListOf<Int>()

        itemsState.value.forEachIndexed { index, itemContent ->
            val listItem = itemContent.item
            var isYOutOfBounds = true
            for (y in listItem.fromY..listItem.toY) {
                if (y in boundaries.fromY..boundaries.toY) {
                    isYOutOfBounds = false
                    break
                }
            }
            if (!isYOutOfBounds) {
                for (x in listItem.fromX..listItem.toX) {
                    if (x in boundaries.fromX..boundaries.toX) {
                        result.add(index)
                        break
                    }
                }
            }
        }

        return result
    }

    fun getItem(index: Int): ListItem? {
        return itemsState.value.getOrNull(index)?.item
    }
}
