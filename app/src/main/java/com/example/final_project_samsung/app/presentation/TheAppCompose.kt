package com.example.final_project_samsung.app.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.final_project_samsung.app.presentation.appNavigation.AppDrawer
import com.example.final_project_samsung.app.presentation.appNavigation.TheAppDestinations
import com.example.final_project_samsung.app.presentation.appNavigation.TheAppNavGraph
import com.example.final_project_samsung.app.presentation.appNavigation.TheAppNavigationActions
import kotlinx.coroutines.launch

@Composable
fun TheAppCompose(widthSizeClass: WindowWidthSizeClass) {

    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        TheAppNavigationActions(navController)
    }

    val coroutineScope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry?.destination?.route ?: TheAppDestinations.GROUP_VIEW_ROUTE


    val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded
    val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)

    ModalNavigationDrawer(
        gesturesEnabled = false,
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                navigateToLazyGroupView = navigationActions.navigateToLazyGroupView,
                navigateToLazyWeekView = navigationActions.navigateToLazyWeekView,
                navigateToLazyEventView = navigationActions.navigateToLazyEventView,
                closeDrawer = { coroutineScope.launch { sizeAwareDrawerState.close() } }
            )
        },
        drawerState = sizeAwareDrawerState,
    ) {
        Row {
            TheAppNavGraph(
                isExpandedScreen = isExpandedScreen,
                navController = navController,
                openDrawer = { coroutineScope.launch { sizeAwareDrawerState.open() } },
            )
        }
    }
}

@Composable
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): DrawerState {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    return if (!isExpandedScreen) {
        drawerState
    } else {
        DrawerState(DrawerValue.Closed)
    }
}
