package com.example.final_project_samsung.app.presentation.appNavigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.final_project_samsung.R

@Composable
fun AppNavRail(
    currentRoute: String,
    navigateToLazyGroupView: () -> Unit,
    navigateToLazyWeekView: () -> Unit,
    navigateToLazyEventView: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        header = {
            Text(text = "TheApp")
        },
        modifier = modifier
    ) {
        Spacer(Modifier.weight(1f))
        NavigationRailItem(
            label = { Text(stringResource(id = R.string.group_view_title)) },
            icon = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null) },
            selected = currentRoute == TheAppDestinations.GROUP_VIEW_ROUTE,
            onClick = { navigateToLazyGroupView() },
            alwaysShowLabel = false
        )
        NavigationRailItem(
            label = { Text(stringResource(id = R.string.week_view_title)) },
            icon = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null) },
            selected = currentRoute == TheAppDestinations.WEEK_VIEW_ROUTE,
            onClick = { navigateToLazyWeekView() },
            alwaysShowLabel = false
        )
        NavigationRailItem(
            label = { Text(stringResource(id = R.string.event_view_title)) },
            icon = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null) },
            selected = currentRoute == TheAppDestinations.EVENT_VIEW_ROUTE,
            onClick = { navigateToLazyEventView() },
            alwaysShowLabel = false
        )

        Spacer(Modifier.weight(1f))
    }
}