package com.example.final_project_samsung.app.presentation.appNavigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.final_project_samsung.app.presentation.events.EventsRoute
import com.example.final_project_samsung.app.presentation.events.EventsViewModel
import com.example.final_project_samsung.app.presentation.groups.GroupsRoute
import com.example.final_project_samsung.app.presentation.groups.GroupsViewModel
import com.example.final_project_samsung.app.presentation.weeks.WeeksRoute
import com.example.final_project_samsung.app.presentation.weeks.WeeksViewModel


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
            val eventsViewModel: EventsViewModel = hiltViewModel()
            EventsRoute(
                eventsViewModel = eventsViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }
    }
}
