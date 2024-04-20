package com.example.final_project_samsung.app.presentation.groups.customLazyLayout.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import com.example.final_project_samsung.app.presentation.groups.State
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun rememberLazyLayoutState(): LazyLayoutState {
    return remember { LazyLayoutState() }
}

@Stable
class LazyLayoutState {

    private val _offsetState = mutableStateOf(
        IntOffset(
            0,
            ((LocalDateTime.now()
                .toEpochSecond(ZoneOffset.UTC) / (60f * 60f)) * State().pdForHour.value).toInt()
        )
    )
    val offsetState = _offsetState

    fun onDrag(offset: IntOffset) {
        val x = (_offsetState.value.x - offset.x).coerceAtLeast(0)
        val y = (_offsetState.value.y - offset.y).coerceAtLeast(0)
        if (x < 100) {
            _offsetState.value = IntOffset(x, y)
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
