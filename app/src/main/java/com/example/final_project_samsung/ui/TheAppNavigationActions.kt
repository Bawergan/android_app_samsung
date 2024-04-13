package com.example.final_project_samsung.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object TheAppDestinations {
    const val LAZY_GROUP_VIEW_ROUTE = "lazyGroupView"
    const val LAZY_WEEK_VIEW_ROUTE = "lazyWeekView"
}

class TheAppNavigationActions(navController: NavHostController) {
    val navigateToLazyGroupView: () -> Unit = {
        navController.navigate(TheAppDestinations.LAZY_GROUP_VIEW_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToLazyWeekView: () -> Unit = {
        navController.navigate(TheAppDestinations.LAZY_WEEK_VIEW_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
