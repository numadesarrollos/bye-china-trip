package com.numadesarrollos.byechinaapp.today

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.numadesarrollos.base.presentation.screen.NDScreen
import com.numadesarrollos.byechinaapp.common.OnResume
import com.numadesarrollos.byechinaapp.itinerary.Activity
import com.numadesarrollos.byechinaapp.itinerary.Day
import com.numadesarrollos.byechinaapp.itinerary.Trip
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TodayScreen(
    onNavigateToDay: (String) -> Unit,
    onCreateTrip: () -> Unit,
) {
    val viewModel: TodayViewModel = koinViewModel()

    OnResume { viewModel.reload() }

    NDScreen(
        viewModel = viewModel,
        onEffect = { effect ->
            when (effect) {
                TodayEffect.NavigateToCreateTrip -> onCreateTrip()
                is TodayEffect.NavigateToDay -> onNavigateToDay(effect.dayId)
            }
        },
    ) { state, onEvent ->
        when {
            state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            state.trip == null -> EmptyTripState(onCreateTrip = { onEvent(TodayEvent.CreateTripClicked) })
            else -> TodayContent(
                trip = state.trip,
                today = state.today,
                activities = state.activities,
                tripProgress = state.tripProgress,
                onTodayCardClick = { onEvent(TodayEvent.TodayCardClicked) },
            )
        }
    }
}

@Composable
private fun EmptyTripState(onCreateTrip: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Nuestro viaje a China",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "🐻🐰",
            style = MaterialTheme.typography.displaySmall,
        )
        Text(
            text = "Todavía no hay un viaje creado.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp),
            textAlign = TextAlign.Center,
        )
        Button(onClick = onCreateTrip) {
            Text("Crear viaje")
        }
    }
}

@Composable
private fun TodayContent(
    trip: Trip,
    today: Day?,
    activities: List<Activity>,
    tripProgress: TripProgress?,
    onTodayCardClick: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
    ) {
        item {
            Text(
                text = "Hola, 🐻 y 🐰",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            val progressText = when (tripProgress) {
                is TripProgress.BeforeTrip -> "Faltan ${tripProgress.daysUntilStart} días · ${trip.name}"
                is TripProgress.DuringTrip -> "Día ${tripProgress.dayNumber} de ${tripProgress.totalDays} · ${trip.name}"
                TripProgress.AfterTrip -> "${trip.name} — viaje terminado"
                null -> trip.name
            }
            Text(
                text = progressText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
            )
        }

        if (today == null) {
            item {
                Text(
                    text = "Hoy no hay nada planeado todavía.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        } else {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Text(
                            text = today.title ?: "Plan del día",
                            style = MaterialTheme.typography.titleLarge,
                        )
                        today.notes?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 4.dp),
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Plan de hoy",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp),
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
                    ActivityRow(activity, onClick = onTodayCardClick)
                }
            }
        }
    }
}

@Composable
private fun ActivityRow(activity: Activity, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            activity.time?.let {
                Text(text = it.toString(), style = MaterialTheme.typography.labelMedium)
            }
            Text(text = activity.title, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
