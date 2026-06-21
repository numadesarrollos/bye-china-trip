package com.numadesarrollos.byechinaapp.itinerary

import com.numadesarrollos.base.domain.dispatcher.NDDispatcherProvider
import com.numadesarrollos.base.presentation.mvi.NDViewModel
import kotlinx.datetime.LocalDate

class TripFormViewModel(
    private val createTripUseCase: CreateTripUseCase,
    dispatcherProvider: NDDispatcherProvider,
) : NDViewModel<TripFormState, TripFormEvent, TripFormEffect>(TripFormState(), dispatcherProvider) {

    override fun onEvent(event: TripFormEvent) {
        when (event) {
            is TripFormEvent.NameChanged -> setState { copy(name = event.name) }
            is TripFormEvent.StartDateChanged -> setState { copy(startDate = event.date) }
            is TripFormEvent.EndDateChanged -> setState { copy(endDate = event.date) }
            is TripFormEvent.OwnerChanged -> setState { copy(createdBy = event.owner) }
            TripFormEvent.SaveClicked -> save()
            TripFormEvent.ErrorDismissed -> setState { copy(error = null) }
        }
    }

    private fun save() {
        val state = currentState
        val startDate = state.startDate.toLocalDateOrNull()
        val endDate = state.endDate.toLocalDateOrNull()

        if (state.name.isBlank() || startDate == null || endDate == null) {
            setState { copy(error = "Rellena nombre y fechas en formato AAAA-MM-DD") }
            return
        }

        setState { copy(isSaving = true, error = null) }
        execute(
            onSuccess = {
                setState { copy(isSaving = false) }
                setEffect { TripFormEffect.Saved }
            },
            onError = { throwable ->
                setState { copy(isSaving = false, error = throwable.message) }
            },
        ) {
            createTripUseCase(
                CreateTripParams(
                    name = state.name,
                    startDate = startDate,
                    endDate = endDate,
                    createdBy = state.createdBy,
                ),
            ).getOrThrow()
        }
    }
}

private fun String.toLocalDateOrNull(): LocalDate? = try {
    LocalDate.parse(this)
} catch (e: IllegalArgumentException) {
    null
}
