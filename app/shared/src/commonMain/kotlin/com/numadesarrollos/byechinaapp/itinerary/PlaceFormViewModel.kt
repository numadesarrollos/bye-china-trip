package com.numadesarrollos.byechinaapp.itinerary

import com.numadesarrollos.base.domain.dispatcher.NDDispatcherProvider
import com.numadesarrollos.base.presentation.mvi.NDViewModel

class PlaceFormViewModel(
    private val tripId: String,
    private val createPlaceUseCase: CreatePlaceUseCase,
    dispatcherProvider: NDDispatcherProvider,
) : NDViewModel<PlaceFormState, PlaceFormEvent, PlaceFormEffect>(PlaceFormState(), dispatcherProvider) {

    override fun onEvent(event: PlaceFormEvent) {
        when (event) {
            is PlaceFormEvent.NameChanged -> setState { copy(name = event.name) }
            is PlaceFormEvent.OwnerChanged -> setState { copy(createdBy = event.owner) }
            PlaceFormEvent.SaveClicked -> save()
            PlaceFormEvent.ErrorDismissed -> setState { copy(error = null) }
        }
    }

    private fun save() {
        val state = currentState
        if (state.name.isBlank()) {
            setState { copy(error = "Pon un nombre para la ciudad") }
            return
        }

        setState { copy(isSaving = true, error = null) }
        execute(
            onSuccess = {
                setState { copy(isSaving = false) }
                setEffect { PlaceFormEffect.Saved }
            },
            onError = { throwable ->
                setState { copy(isSaving = false, error = throwable.message) }
            },
        ) {
            createPlaceUseCase(
                CreatePlaceParams(tripId = tripId, name = state.name, createdBy = state.createdBy),
            ).getOrThrow()
        }
    }
}
