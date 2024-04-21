package com.example.final_project_samsung.app.presentation.appNavigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.final_project_samsung.app.presentation.addEditScreen.forEvent.AddEditEventBottomSheet
import com.example.final_project_samsung.app.presentation.addEditScreen.forEvent.AddEditGroupBottomSheet
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
            val groupsViewModel: GroupsViewModel = hiltViewModel()

            GroupsRoute(
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
                navController = navController,
                groupsViewModel = groupsViewModel
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
                navController
            )
        }
        composable(
            route = TheAppDestinations.ADD_EDIT_EVENT_ROUTE + "?eventId={eventId}",
            arguments = listOf(
                navArgument(name = "eventId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditEventBottomSheet(navController = navController)
        }
        composable(
            route = TheAppDestinations.ADD_EDIT_GROUP_ROUTE + "?groupId={groupId}",
            arguments = listOf(
                navArgument(name = "groupId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditGroupBottomSheet(navController = navController)
        }

    }
}
