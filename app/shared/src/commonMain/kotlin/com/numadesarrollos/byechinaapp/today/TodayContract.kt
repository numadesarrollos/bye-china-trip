package com.numadesarrollos.byechinaapp.today

import com.numadesarrollos.base.presentation.mvi.NDUiEffect
import com.numadesarrollos.base.presentation.mvi.NDUiEvent
import com.numadesarrollos.base.presentation.mvi.NDUiState
import com.numadesarrollos.byechinaapp.itinerary.Activity
import com.numadesarrollos.byechinaapp.itinerary.Day
import com.numadesarrollos.byechinaapp.itinerary.Trip

data class TodayState(
    val isLoading: Boolean = true,
    val trip: Trip? = null,
    val today: Day? = null,
    val activities: List<Activity> = emptyList(),
    val tripProgress: TripProgress? = null,
) : NDUiState

sealed interface TripProgress {
    data class BeforeTrip(val daysUntilStart: Int) : TripProgress
    data class DuringTrip(val dayNumber: Int, val totalDays: Int) : TripProgress
    data object AfterTrip : TripProgress
}

sealed interface TodayEvent : NDUiEvent {
    data object CreateTripClicked : TodayEvent
    data object TodayCardClicked : TodayEvent
}

sealed interface TodayEffect : NDUiEffect {
    data object NavigateToCreateTrip : TodayEffect
    data class NavigateToDay(val dayId: String) : TodayEffect
}
