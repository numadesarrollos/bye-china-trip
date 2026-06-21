package com.numadesarrollos.byechinaapp.itinerary

import com.numadesarrollos.base.presentation.mvi.NDUiEffect
import com.numadesarrollos.base.presentation.mvi.NDUiEvent
import com.numadesarrollos.base.presentation.mvi.NDUiState

data class DayDetailState(
    val isLoading: Boolean = true,
    val day: Day? = null,
    val activities: List<Activity> = emptyList(),
    val locations: List<Location> = emptyList(),
) : NDUiState

sealed interface DayDetailEvent : NDUiEvent {
    data object Retry : DayDetailEvent
}

sealed interface DayDetailEffect : NDUiEffect
