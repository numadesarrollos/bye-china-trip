package com.numadesarrollos.byechinaapp.nav

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

private data class BottomTab(val route: String, val label: String, val emoji: String)

private val tabs = listOf(
    BottomTab(Routes.TODAY, "Hoy", "🏠"),
    BottomTab(Routes.ITINERARY, "Itinerario", "🗺️"),
    BottomTab(Routes.FLIGHTS, "Vuelos", "✈️"),
    BottomTab(Routes.TRAINS, "Trenes", "🚄"),
    BottomTab(Routes.MORE, "Más", "⋯"),
)

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
) {
    NavigationBar {
        tabs.forEach { tab ->
            NavigationBarItem(
                selected = currentRoute == tab.route,
                onClick = { onNavigate(tab.route) },
                icon = { Text(tab.emoji) },
                label = { Text(tab.label) },
            )
        }
    }
}
