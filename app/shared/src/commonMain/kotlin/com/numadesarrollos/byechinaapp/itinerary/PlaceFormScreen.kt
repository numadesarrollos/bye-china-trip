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
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceFormScreen(tripId: String, onDone: () -> Unit, onBack: () -> Unit = onDone) {
    val viewModel: PlaceFormViewModel = koinViewModel(parameters = { parametersOf(tripId) })

    NDScreen(
        viewModel = viewModel,
        onEffect = { effect ->
            when (effect) {
                PlaceFormEffect.Saved -> onDone()
            }
        },
    ) { state, onEvent ->
        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(state.error) {
            state.error?.let {
                snackbarHostState.showSnackbar(it)
                onEvent(PlaceFormEvent.ErrorDismissed)
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Nueva ciudad") },
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
                    onValueChange = { onEvent(PlaceFormEvent.NameChanged(it)) },
                    label = { Text("Nombre de la ciudad") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(
                    text = "Quién la añade",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(top = 20.dp, bottom = 8.dp),
                )
                OwnerSelector(
                    selected = state.createdBy,
                    onSelect = { onEvent(PlaceFormEvent.OwnerChanged(it)) },
                )
                Button(
                    onClick = { onEvent(PlaceFormEvent.SaveClicked) },
                    enabled = !state.isSaving,
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                ) {
                    Text("Añadir ciudad")
                }
            }
        }
    }
}
