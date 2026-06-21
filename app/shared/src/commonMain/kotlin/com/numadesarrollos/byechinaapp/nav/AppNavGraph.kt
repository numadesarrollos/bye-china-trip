package com.numadesarrollos.byechinaapp.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.savedstate.read
import com.numadesarrollos.byechinaapp.auth.LoginScreen
import com.numadesarrollos.byechinaapp.common.ComingSoonScreen
import com.numadesarrollos.byechinaapp.itinerary.DayDetailScreen
import com.numadesarrollos.byechinaapp.itinerary.DayFormScreen
import com.numadesarrollos.byechinaapp.itinerary.ItineraryScreen
import com.numadesarrollos.byechinaapp.itinerary.PlaceFormScreen
import com.numadesarrollos.byechinaapp.itinerary.TripFormScreen
import com.numadesarrollos.byechinaapp.today.TodayScreen

@Composable
fun AppNavGraph(startLoggedIn: Boolean) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in Routes.bottomNavRoutes) {
                AppBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        },
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = if (startLoggedIn) Routes.TODAY else Routes.LOGIN,
            modifier = Modifier.padding(contentPadding),
        ) {
            composable(Routes.LOGIN) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Routes.TODAY) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    },
                )
            }
            composable(Routes.TODAY) {
                TodayScreen(
                    onNavigateToDay = { dayId -> navController.navigate(Routes.dayDetail(dayId)) },
                    onCreateTrip = { navController.navigate(Routes.TRIP_FORM) },
                )
            }
            composable(Routes.ITINERARY) {
                ItineraryScreen(
                    onNavigateToDay = { dayId -> navController.navigate(Routes.dayDetail(dayId)) },
                    onAddPlace = { tripId -> navController.navigate(Routes.placeForm(tripId)) },
                    onAddDay = { placeId -> navController.navigate(Routes.dayForm(placeId)) },
                    onCreateTrip = { navController.navigate(Routes.TRIP_FORM) },
                )
            }
            composable(Routes.FLIGHTS) { ComingSoonScreen("Vuelos") }
            composable(Routes.TRAINS) { ComingSoonScreen("Trenes") }
            composable(Routes.MORE) { ComingSoonScreen("Más") }

            composable(Routes.TRIP_FORM) {
                TripFormScreen(
                    onDone = { navController.popBackStack() },
                    onBack = { navController.popBackStack() },
                )
            }
            composable(
                route = Routes.PLACE_FORM_PATTERN,
                arguments = listOf(navArgument("tripId") { type = NavType.StringType }),
            ) { backStack ->
                val tripId = backStack.arguments?.read { getStringOrNull("tripId") }.orEmpty()
                PlaceFormScreen(
                    tripId = tripId,
                    onDone = { navController.popBackStack() },
                    onBack = { navController.popBackStack() },
                )
            }
            composable(
                route = Routes.DAY_FORM_PATTERN,
                arguments = listOf(navArgument("placeId") { type = NavType.StringType }),
            ) { backStack ->
                val placeId = backStack.arguments?.read { getStringOrNull("placeId") }.orEmpty()
                DayFormScreen(
                    placeId = placeId,
                    onDone = { navController.popBackStack() },
                    onBack = { navController.popBackStack() },
                )
            }
            composable(
                route = Routes.DAY_DETAIL_PATTERN,
                arguments = listOf(navArgument("dayId") { type = NavType.StringType }),
            ) { backStack ->
                val dayId = backStack.arguments?.read { getStringOrNull("dayId") }.orEmpty()
                DayDetailScreen(dayId = dayId, onBack = { navController.popBackStack() })
            }
        }
    }
}
