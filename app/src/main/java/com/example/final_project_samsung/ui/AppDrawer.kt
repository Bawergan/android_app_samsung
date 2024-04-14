package com.example.final_project_samsung.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.final_project_samsung.R
import kotlinx.coroutines.Job

@Composable
fun AppDrawer(
    currentRoute: String,
    navigateToLazyGroupView: () -> Unit,
    navigateToLazyWeekView: () -> Unit,
    navigateToLazyEventView: () -> Unit,
    closeDrawer: () -> Job,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(modifier) {
        Text(text = "TheApp", modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp))
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.group_view_title)) },
            icon = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null) },
            selected = currentRoute == TheAppDestinations.GROUP_VIEW_ROUTE,
            onClick = { navigateToLazyGroupView(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.week_view_title)) },
            icon = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null) },
            selected = currentRoute == TheAppDestinations.WEEK_VIEW_ROUTE,
            onClick = { navigateToLazyWeekView(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.event_view_title)) },
            icon = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null) },
            selected = currentRoute == TheAppDestinations.EVENT_VIEW_ROUTE,
            onClick = { navigateToLazyEventView(); closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

    }
}
