package com.numadesarrollos.byechinaapp.itinerary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.numadesarrollos.base.presentation.screen.NDScreen
import com.numadesarrollos.byechinaapp.common.OnResume
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItineraryScreen(
    onNavigateToDay: (String) -> Unit,
    onAddPlace: (String) -> Unit,
    onAddDay: (String) -> Unit,
    onCreateTrip: () -> Unit,
) {
    val viewModel: ItineraryViewModel = koinViewModel()

    OnResume { viewModel.reload() }

    NDScreen(
        viewModel = viewModel,
        onEffect = { effect ->
            when (effect) {
                ItineraryEffect.NavigateToCreateTrip -> onCreateTrip()
                is ItineraryEffect.NavigateToAddPlace -> onAddPlace(effect.tripId)
                is ItineraryEffect.NavigateToAddDay -> onAddDay(effect.placeId)
                is ItineraryEffect.NavigateToDay -> onNavigateToDay(effect.dayId)
            }
        },
    ) { state, onEvent ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Itinerario") },
                    actions = {
                        if (state.trip != null) {
                            IconButton(onClick = { onEvent(ItineraryEvent.AddPlaceClicked) }) {
                                Text("+", style = MaterialTheme.typography.headlineSmall)
                            }
                        }
                    },
                )
            },
        ) { padding ->
            when {
                state.isLoading -> Box(
                    Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center,
                ) { CircularProgressIndicator() }

                state.trip == null -> EmptyItineraryState(
                    modifier = Modifier.padding(padding),
                    onCreateTrip = { onEvent(ItineraryEvent.CreateTripClicked) },
                )

                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentPadding = PaddingValues(16.dp),
                ) {
                    items(state.places) { placeWithDays ->
                        PlaceGroup(
                            placeWithDays = placeWithDays,
                            expanded = placeWithDays.place.id in state.expandedPlaceIds,
                            onToggle = { onEvent(ItineraryEvent.TogglePlace(placeWithDays.place.id)) },
                            onDayClick = { dayId -> onEvent(ItineraryEvent.DayClicked(dayId)) },
                            onAddDay = { onEvent(ItineraryEvent.AddDayClicked(placeWithDays.place.id)) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyItineraryState(modifier: Modifier = Modifier, onCreateTrip: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Todavía no hay un viaje creado.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
        Button(onClick = onCreateTrip, modifier = Modifier.padding(top = 16.dp)) {
            Text("Crear viaje")
        }
    }
}

@Composable
private fun PlaceGroup(
    placeWithDays: PlaceWithDays,
    expanded: Boolean,
    onToggle: () -> Unit,
    onDayClick: (String) -> Unit,
    onAddDay: () -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().clickable { onToggle() }.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = placeWithDays.place.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = if (expanded) "▲" else "▼",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            if (expanded) {
                placeWithDays.days.forEachIndexed { index, day ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .clickable { onDayClick(day.id) }
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column {
                            Text(
                                text = "Día ${index + 1}",
                                style = MaterialTheme.typography.labelMedium,
                                color = if (day.isSpecial) {
                                    MaterialTheme.colorScheme.tertiary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                },
                            )
                            Text(
                                text = day.title ?: day.date.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        Text("›", style = MaterialTheme.typography.titleMedium)
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { onAddDay() }.padding(16.dp),
                ) {
                    Text(
                        text = "+ Añadir día",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}
