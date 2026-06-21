package com.numadesarrollos.byechinaapp.itinerary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.numadesarrollos.base.presentation.screen.NDScreen
import com.numadesarrollos.byechinaapp.common.OwnerSelector
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripFormScreen(onDone: () -> Unit, onBack: () -> Unit = onDone) {
    val viewModel: TripFormViewModel = koinViewModel()

    NDScreen(
        viewModel = viewModel,
        onEffect = { effect ->
            when (effect) {
                TripFormEffect.Saved -> onDone()
            }
        },
    ) { state, onEvent ->
        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(state.error) {
            state.error?.let {
                snackbarHostState.showSnackbar(it)
                onEvent(TripFormEvent.ErrorDismissed)
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Nuevo viaje") },
                    navigationIcon = {
                        IconButton(onClick = onBack) { Text("←") }
                    },
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { padding ->
            Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
                OutlinedTextField(
                    value = state.name,
                    onValueChange = { onEvent(TripFormEvent.NameChanged(it)) },
                    label = { Text("Nombre del viaje") },
                    modifier = Modifier.fillMaxWidth(),
                )
                OutlinedTextField(
                    value = state.startDate,
                    onValueChange = { onEvent(TripFormEvent.StartDateChanged(it)) },
                    label = { Text("Fecha de inicio (AAAA-MM-DD)") },
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                )
                OutlinedTextField(
                    value = state.endDate,
                    onValueChange = { onEvent(TripFormEvent.EndDateChanged(it)) },
                    label = { Text("Fecha de fin (AAAA-MM-DD)") },
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                )
                Text(
                    text = "Quién lo crea",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(top = 20.dp, bottom = 8.dp),
                )
                OwnerSelector(
                    selected = state.createdBy,
                    onSelect = { onEvent(TripFormEvent.OwnerChanged(it)) },
                )
                Button(
                    onClick = { onEvent(TripFormEvent.SaveClicked) },
                    enabled = !state.isSaving,
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                ) {
                    Text("Crear viaje")
                }
            }
        }
    }
}
