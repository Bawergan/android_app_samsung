package com.example.final_project_samsung.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.final_project_samsung.ui.events.EventsRoute
import com.example.final_project_samsung.ui.events.EventsViewModel
import com.example.final_project_samsung.ui.groups.GroupsRoute
import com.example.final_project_samsung.ui.groups.GroupsViewModel
import com.example.final_project_samsung.ui.weeks.WeeksRoute
import com.example.final_project_samsung.ui.weeks.WeeksViewModel

@Composable
fun TheAppNavGraph(
    modifier: Modifier = Modifier,
    isExpandedScreen: Boolean,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit,
    startDestination: String = TheAppDestinations.GROUP_VIEW_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = TheAppDestinations.GROUP_VIEW_ROUTE,
        ) {
            val groupsViewModel: GroupsViewModel = viewModel()
            GroupsRoute(
                groupsViewModel = groupsViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }
        composable(
            route = TheAppDestinations.WEEK_VIEW_ROUTE,
        ) {
            val weeksViewModel: WeeksViewModel = viewModel()
            WeeksRoute(
                weeksViewModel = weeksViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }
        composable(
            route = TheAppDestinations.EVENT_VIEW_ROUTE,
        ) {
            val eventsViewModel: EventsViewModel = viewModel()
            EventsRoute(
                eventsViewModel = eventsViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }
    }
}
