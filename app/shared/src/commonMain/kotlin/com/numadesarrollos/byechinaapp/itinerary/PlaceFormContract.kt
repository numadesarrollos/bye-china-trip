package com.numadesarrollos.byechinaapp.itinerary

import com.numadesarrollos.base.presentation.mvi.NDUiEffect
import com.numadesarrollos.base.presentation.mvi.NDUiEvent
import com.numadesarrollos.base.presentation.mvi.NDUiState
import com.numadesarrollos.byechinaapp.domain.Owner

data class PlaceFormState(
    val name: String = "",
    val createdBy: Owner = Owner.BEAR,
    val isSaving: Boolean = false,
    val error: String? = null,
) : NDUiState

sealed interface PlaceFormEvent : NDUiEvent {
    data class NameChanged(val name: String) : PlaceFormEvent
    data class OwnerChanged(val owner: Owner) : PlaceFormEvent
    data object SaveClicked : PlaceFormEvent
    data object ErrorDismissed : PlaceFormEvent
}

sealed interface PlaceFormEffect : NDUiEffect {
    data object Saved : PlaceFormEffect
}
