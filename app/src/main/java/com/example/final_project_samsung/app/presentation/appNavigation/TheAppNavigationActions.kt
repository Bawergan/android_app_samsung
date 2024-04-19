package com.example.final_project_samsung.app.presentation.appNavigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object TheAppDestinations {
    const val GROUP_VIEW_ROUTE = "groupView"
    const val WEEK_VIEW_ROUTE = "weekView"
    const val EVENT_VIEW_ROUTE = "eventView"
    const val ADD_EDIT_EVENT_ROUTE = "addEditEventRoute"
    const val ADD_EDIT_GROUP_ROUTE = "addEditGroupRoute"
}

class TheAppNavigationActions(navController: NavHostController) {
    val navigateToLazyGroupView: () -> Unit = {
        navController.navigate(TheAppDestinations.GROUP_VIEW_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToLazyWeekView: () -> Unit = {
        navController.navigate(TheAppDestinations.WEEK_VIEW_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToLazyEventView: () -> Unit = {
        navController.navigate(TheAppDestinations.EVENT_VIEW_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
