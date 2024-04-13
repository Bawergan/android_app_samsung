package com.example.final_project_samsung.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.final_project_samsung.ui.groups.GroupsRoute
import com.example.final_project_samsung.ui.groups.GroupsViewModel
import com.example.final_project_samsung.ui.weeks.WeeksRoute
import com.example.final_project_samsung.ui.weeks.WeeksViewModel

@Composable
fun TheAppNavGraph(
    isExpandedScreen: Boolean,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit,
    startDestination: String = TheAppDestinations.LAZY_GROUP_VIEW_ROUTE,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = TheAppDestinations.LAZY_GROUP_VIEW_ROUTE,
//            deepLinks = listOf(
//                navDeepLink {
//                    uriPattern =
//                        "$JETNEWS_APP_URI/${JetnewsDestinations.HOME_ROUTE}?$POST_ID={$POST_ID}"
//                }
//            )
        ) {
            val groupsViewModel: GroupsViewModel = viewModel()
            GroupsRoute(
                GroupsViewModel = groupsViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }
        composable(
            route = TheAppDestinations.LAZY_WEEK_VIEW_ROUTE,
//            deepLinks = listOf(
//                navDeepLink {
//                    uriPattern =
//                        "$JETNEWS_APP_URI/${JetnewsDestinations.HOME_ROUTE}?$POST_ID={$POST_ID}"
//                }
//            )
        ) {
            val weeksViewModel: WeeksViewModel = viewModel()
            WeeksRoute(
                weeksViewModel = weeksViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }
    }
}