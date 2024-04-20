package com.example.final_project_samsung.app.presentation.groups.customLazyLayout.layout

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import com.example.final_project_samsung.app.presentation.groups.customLazyLayout.ListItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomLazyLayout(
    modifier: Modifier = Modifier,
    state: LazyLayoutState = rememberLazyLayoutState(),
    content: CustomLazyListScope.() -> Unit
) {
    val itemProvider = rememberItemProvider(content)

    LazyLayout(modifier = modifier
        .clipToBounds()
        .lazyLayoutPointerInput(state),
        itemProvider = { itemProvider }) { constraints ->
        val boundaries = state.getBoundaries(constraints)
        val indexes = itemProvider.getItemIndexesInRange(boundaries)

        val indexesWithPlaceables = indexes.associateWith {
            measure(it, Constraints())
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            indexesWithPlaceables.forEach { (index, placeables) ->
                val item = itemProvider.getItem(index)
                item?.let { placeItem(state, item, placeables) }
            }
        }

    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
private fun Modifier.lazyLayoutPointerInput(state: LazyLayoutState): Modifier {
    return pointerInput(Unit) {
        detectDragGestures { change, dragAmount ->
            change.consume()
            state.onDrag(IntOffset(dragAmount.x.toInt(), dragAmount.y.toInt()))
        }
    }
}

private fun Placeable.PlacementScope.placeItem(
    state: LazyLayoutState,
    listItem: ListItem,
    placeables: List<Placeable>
) {
    val xPosition = listItem.x - state.offsetState.value.x
    val yPosition = listItem.y - state.offsetState.value.y

    placeables.forEach { placeable ->
        placeable.placeRelative(
            xPosition,
            yPosition
        )
    }
}
