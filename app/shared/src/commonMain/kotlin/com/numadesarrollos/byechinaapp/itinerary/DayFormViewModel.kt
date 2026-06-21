package com.numadesarrollos.byechinaapp.itinerary

import com.numadesarrollos.base.domain.dispatcher.NDDispatcherProvider
import com.numadesarrollos.base.presentation.mvi.NDViewModel
import kotlinx.datetime.LocalDate

class DayFormViewModel(
    private val placeId: String,
    private val createDayUseCase: CreateDayUseCase,
    dispatcherProvider: NDDispatcherProvider,
) : NDViewModel<DayFormState, DayFormEvent, DayFormEffect>(DayFormState(), dispatcherProvider) {

    override fun onEvent(event: DayFormEvent) {
        when (event) {
            is DayFormEvent.DateChanged -> setState { copy(date = event.date) }
            is DayFormEvent.TitleChanged -> setState { copy(title = event.title) }
            is DayFormEvent.NotesChanged -> setState { copy(notes = event.notes) }
            is DayFormEvent.IsSpecialChanged -> setState { copy(isSpecial = event.isSpecial) }
            is DayFormEvent.OwnerChanged -> setState { copy(createdBy = event.owner) }
            DayFormEvent.SaveClicked -> save()
            DayFormEvent.ErrorDismissed -> setState { copy(error = null) }
        }
    }

    private fun save() {
        val state = currentState
        val date = state.date.toLocalDateOrNull()
        if (date == null) {
            setState { copy(error = "Pon una fecha válida en formato AAAA-MM-DD") }
            return
        }

        setState { copy(isSaving = true, error = null) }
        execute(
            onSuccess = {
                setState { copy(isSaving = false) }
                setEffect { DayFormEffect.Saved }
            },
            onError = { throwable ->
                setState { copy(isSaving = false, error = throwable.message) }
            },
        ) {
            createDayUseCase(
                CreateDayParams(
                    placeId = placeId,
                    date = date,
                    title = state.title.ifBlank { null },
                    isSpecial = state.isSpecial,
                    notes = state.notes.ifBlank { null },
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
