package com.numadesarrollos.byechinaapp.itinerary

import com.numadesarrollos.base.presentation.mvi.NDUiEffect
import com.numadesarrollos.base.presentation.mvi.NDUiEvent
import com.numadesarrollos.base.presentation.mvi.NDUiState
import com.numadesarrollos.byechinaapp.domain.Owner

data class TripFormState(
    val name: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val createdBy: Owner = Owner.BEAR,
    val isSaving: Boolean = false,
    val error: String? = null,
) : NDUiState

sealed interface TripFormEvent : NDUiEvent {
    data class NameChanged(val name: String) : TripFormEvent
    data class StartDateChanged(val date: String) : TripFormEvent
    data class EndDateChanged(val date: String) : TripFormEvent
    data class OwnerChanged(val owner: Owner) : TripFormEvent
    data object SaveClicked : TripFormEvent
    data object ErrorDismissed : TripFormEvent
}

sealed interface TripFormEffect : NDUiEffect {
    data object Saved : TripFormEffect
}
