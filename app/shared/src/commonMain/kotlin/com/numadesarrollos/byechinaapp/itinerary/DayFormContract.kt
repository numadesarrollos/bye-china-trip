package com.numadesarrollos.byechinaapp.itinerary

import com.numadesarrollos.base.presentation.mvi.NDUiEffect
import com.numadesarrollos.base.presentation.mvi.NDUiEvent
import com.numadesarrollos.base.presentation.mvi.NDUiState
import com.numadesarrollos.byechinaapp.domain.Owner

data class DayFormState(
    val date: String = "",
    val title: String = "",
    val notes: String = "",
    val isSpecial: Boolean = false,
    val createdBy: Owner = Owner.BEAR,
    val isSaving: Boolean = false,
    val error: String? = null,
) : NDUiState

sealed interface DayFormEvent : NDUiEvent {
    data class DateChanged(val date: String) : DayFormEvent
    data class TitleChanged(val title: String) : DayFormEvent
    data class NotesChanged(val notes: String) : DayFormEvent
    data class IsSpecialChanged(val isSpecial: Boolean) : DayFormEvent
    data class OwnerChanged(val owner: Owner) : DayFormEvent
    data object SaveClicked : DayFormEvent
    data object ErrorDismissed : DayFormEvent
}

sealed interface DayFormEffect : NDUiEffect {
    data object Saved : DayFormEffect
}
