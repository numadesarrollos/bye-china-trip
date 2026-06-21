package com.numadesarrollos.byechinaapp.itinerary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.numadesarrollos.base.presentation.screen.NDScreen
import com.numadesarrollos.byechinaapp.ui.theme.customColors
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayDetailScreen(dayId: String, onBack: () -> Unit) {
    val viewModel: DayDetailViewModel = koinViewModel(parameters = { parametersOf(dayId) })

    NDScreen(viewModel = viewModel) { state, _ ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(state.day?.title ?: "Detalle del día") },
                    navigationIcon = {
                        IconButton(onClick = onBack) { Text("←") }
                    },
                )
            },
        ) { padding ->
            when {
                state.isLoading -> Box(
                    Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center,
                ) { CircularProgressIndicator() }

                state.day == null -> Box(
                    Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center,
                ) { Text("No se encontró el día") }

                else -> DayDetailContent(
                    day = state.day,
                    activities = state.activities,
                    locations = state.locations,
                    modifier = Modifier.padding(padding),
                )
            }
        }
    }
}

@Composable
private fun DayDetailContent(
    day: Day,
    activities: List<Activity>,
    locations: List<Location>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(
                        if (day.isSpecial) {
                            MaterialTheme.customColors.gold.copy(alpha = 0.2f)
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        },
                    )
                    .padding(16.dp),
            ) {
                Column {
                    Text(
                        text = day.date.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        color = if (day.isSpecial) MaterialTheme.customColors.gold else MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = day.title ?: "Plan del día",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    day.notes?.let {
                        Text(text = it, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

        item {
            Text(
                text = "Plan del día",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp),
            )
        }
        if (activities.isEmpty()) {
            item {
                Text(
                    text = "Sin actividades todavía.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        } else {
            items(activities) { activity ->
                Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        activity.time?.let {
                            Text(text = it.toString(), style = MaterialTheme.typography.labelMedium)
                        }
                        Text(text = activity.title, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }

        item {
            Text(
                text = "Ubicaciones",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp),
            )
        }
        if (locations.isEmpty()) {
            item {
                Text(
                    text = "Sin ubicaciones todavía.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        } else {
            items(locations) { location ->
                Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = location.name, style = MaterialTheme.typography.bodyLarge)
                        location.address?.let {
                            Text(text = it, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
