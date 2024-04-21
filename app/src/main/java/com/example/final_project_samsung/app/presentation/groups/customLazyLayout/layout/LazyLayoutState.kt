package com.example.final_project_samsung.app.presentation.groups.customLazyLayout.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset

@Composable
fun rememberLazyLayoutState(): LazyLayoutState {
    return remember { LazyLayoutState() }
}

@Stable
class LazyLayoutState {

    private val _offsetLayoutState = mutableStateOf(
        IntOffset(
            0,
            0
        )
    )
    val offsetState = _offsetLayoutState

    fun onDrag(offset: IntOffset) {
        val x = (_offsetLayoutState.value.x - offset.x).coerceAtLeast(0)
        val y = (_offsetLayoutState.value.y - offset.y)
        if (x < 100) {
            _offsetLayoutState.value = IntOffset(x, y)
        }
    }

    fun getBoundaries(
        constraints: Constraints,
        threshold: Int = 500
    ): ViewBoundaries {
        return ViewBoundaries(
            fromX = offsetState.value.x - threshold,
            toX = constraints.maxWidth + offsetState.value.x + threshold,
            fromY = offsetState.value.y - threshold,
            toY = constraints.maxHeight + offsetState.value.y + threshold
        )
    }
}
